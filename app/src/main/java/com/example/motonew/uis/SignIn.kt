package com.example.motonew.uis

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.motonew.MainActivity
import com.example.motonew.R
import com.example.motonew.databinding.ActivitySignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class SignIn : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= DataBindingUtil.setContentView(this,R.layout.activity_sign_in)

        auth=FirebaseAuth.getInstance()

        val gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("301626032970-bnqot6s74pd0oo4gieiepg40ph8sjgr1.apps.googleusercontent.com")
            .requestEmail()
            .build()

        val googleSignInClient= GoogleSignIn.getClient(this,gso)

        with(binding){
            btnGoogle.setOnClickListener{
                val signInIntent=googleSignInClient.signInIntent
                launcher.launch(signInIntent)
            }
            btnLogIn.setOnClickListener {
                val userEmail = etEmail.text.toString().trim()  // Get email from EditText
                val userPassword = etPassword.text.toString().trim()  // Get password from EditText

                if (userEmail.isBlank() || userPassword.isBlank()) {
                    // Show toast if email or password is blank
                    Toast.makeText(this@SignIn, "Please fill all the blanks", Toast.LENGTH_SHORT).show()
                } else {
                    // Perform Firebase email/password authentication
                    auth.signInWithEmailAndPassword(userEmail, userPassword)
                        .addOnCompleteListener(this@SignIn) { task ->
                            if (task.isSuccessful) {
                                // Login successful, navigate to com.example.valowiki.MainActivity
                                Toast.makeText(this@SignIn, "Login Successful", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@SignIn, MainActivity::class.java))
                                finish()  // Close the SignIn activity
                            } else {
                                // Login failed, show error message
                                Toast.makeText(this@SignIn, "Login Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }

        }

    }
    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?){
        val credential = GoogleAuthProvider.getCredential(account?.idToken,null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Google sign-in successful, navigate to com.example.valowiki.MainActivity
                    Toast.makeText(this, "Google Sign-In Successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()  // Close the SignIn activity
                } else {
                    // Google sign-in failed, show error message
                    Toast.makeText(this, "Google Sign-In Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }

    }
    private val launcher= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->
        if (result.resultCode== RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account=task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)


            }catch (e: ApiException){
                Toast.makeText(this, "Google Sign-In Failed: ${e.message}", Toast.LENGTH_SHORT).show()

            }
        }

    }

}