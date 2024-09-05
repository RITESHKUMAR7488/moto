package com.example.motonew.uis

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.motonew.MainActivity
import com.example.motonew.R
import com.example.motonew.databinding.ActivitySignuPBinding
import com.example.motonew.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SIGNuP : AppCompatActivity() {
    private lateinit var binding: ActivitySignuPBinding
    private var db = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signu_p)
        with(binding) {
            auth = FirebaseAuth.getInstance()
            btnIntent.setOnClickListener {
                val intent = Intent(this@SIGNuP, SignIn::class.java)
                startActivity(intent)
            }
            btnSignup.setOnClickListener {
                val username = binding.etUsername.text.toString()
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                val repeatPassword = binding.etRepeatPassword.text.toString()
                if (email.isBlank() || username.isBlank() || password.isBlank() || repeatPassword.isBlank()) {
                    Toast.makeText(this@SIGNuP, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
                }
                else if(password != repeatPassword){
                    Toast.makeText(this@SIGNuP, "Password do not match", Toast.LENGTH_SHORT).show()
                }
                else{
                        auth.createUserWithEmailAndPassword(email, password).
                                addOnCompleteListener(this@SIGNuP){task->
                                    if(task.isSuccessful){
                                        Toast.makeText(
                                            this@SIGNuP,
                                            "Sign up success",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        val sharedPref=getSharedPreferences("user_data", MODE_PRIVATE)
                                        val editor=sharedPref.edit()
                                        editor.putBoolean("loggedIn",true)
                                        editor.apply()

                                        val user=User(username,email,password)
                                        val db=Firebase.firestore

                                        db.collection("users")
                                            .add(user)
                                            .addOnSuccessListener { documentReference ->
                                                startActivity(
                                                    Intent(
                                                        this@SIGNuP,
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
                                            this@SIGNuP,
                                            "Registration failed :${task.exception?.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    }
                                }
                }
            }
        }
    }
}