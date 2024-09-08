package com.example.motonew.uis

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.motonew.MainActivity
import com.example.motonew.R
import com.example.motonew.databinding.ActivityRegisterBinding
import com.example.motonew.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private var db = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=DataBindingUtil.setContentView(this,R.layout.activity_register)
        with(binding) {
            auth = FirebaseAuth.getInstance()
            btnSignup.setOnClickListener {
                val username = binding.etUsername.text.toString()
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                val repeatPassword = binding.etRepeatPassword.text.toString()
                if (email.isBlank() || username.isBlank() || password.isBlank() || repeatPassword.isBlank()) {
                    Toast.makeText(this@Register, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
                }
                else if(password != repeatPassword){
                    Toast.makeText(this@Register, "Password do not match", Toast.LENGTH_SHORT).show()
                }
                else{
                    auth.createUserWithEmailAndPassword(email, password).
                    addOnCompleteListener(this@Register){task->
                        if(task.isSuccessful){
                            Toast.makeText(
                                this@Register,
                                "Sign up success",
                                Toast.LENGTH_SHORT
                            ).show()

                            val sharedPref = getSharedPreferences("motonew", MODE_PRIVATE)
                            val editor = sharedPref.edit()
                            editor.putBoolean("loggedIn", true)
                            editor.apply()


                            val user=User(username,email,password)
                            val db=Firebase.firestore

                            db.collection("users")
                                .add(user)
                                .addOnSuccessListener { documentReference ->
                                    startActivity(
                                        Intent(
                                            this@Register,
                                            MainActivity::class.java
                                        )
                                    )
                                    finish()
                                    Log.d(
                                        "response",
                                        "DocumentSnapshot added with ID: ${documentReference.id}"
                                    )
                                }
                                .addOnFailureListener { e ->
                                    Log.w("response", "Error adding document", e)
                                }

                        }
                        else {
                            Toast.makeText(
                                this@Register,
                                "Registration failed :${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                }
            }
            btnintent.setOnClickListener {
                val intent=Intent(this@Register,SignIn::class.java)
                startActivity(intent)
            }
        }
    }
}