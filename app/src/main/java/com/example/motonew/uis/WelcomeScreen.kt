package com.example.motonew.uis

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.motonew.R
import com.example.motonew.databinding.ActivityWelcomeScreenBinding

class WelcomeScreen : AppCompatActivity() {
    lateinit var binding: ActivityWelcomeScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= DataBindingUtil.setContentView(this,R.layout.activity_welcome_screen)
        with(binding){
            btnStart.setOnClickListener {
                val intent= Intent(this@WelcomeScreen,SignIn::class.java)
                startActivity(intent)
            }

        }
    }
}