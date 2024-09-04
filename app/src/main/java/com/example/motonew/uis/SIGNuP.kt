package com.example.motonew.uis

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.motonew.R
import com.example.motonew.databinding.ActivitySignuPBinding

class SIGNuP : AppCompatActivity() {
    private lateinit var binding: ActivitySignuPBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= DataBindingUtil.setContentView(this,R.layout.activity_signu_p)
        with(binding){
            btnIntent.setOnClickListener {
                val intent= Intent(this@SIGNuP,SignIn::class.java)
                startActivity(intent)
            }
            btnSignup.setOnClickListener{
                val intent=Intent(this@SIGNuP,WelcomeScreen::class.java)
                startActivity(intent)
            }
        }
    }
}