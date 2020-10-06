package com.example.ks.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.ks.R

class SplashScreen : AppCompatActivity() {
    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        handler=Handler()
    handler.postDelayed({
        val intent =Intent(this, LoginSignUpActivity::class.java)
        startActivity(intent)
        finish()
    },3000)
    }
}