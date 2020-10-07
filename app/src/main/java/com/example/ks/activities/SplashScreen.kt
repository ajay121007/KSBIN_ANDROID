package com.example.ks.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.ks.R
import com.example.ks.activities.dashboard.DashBoardActivity
import com.example.ks.activities.loginsignup.LoginSignUpActivity
import com.example.ks.sharedpref.SharedPreferenceHelper
import org.koin.android.ext.android.inject

class SplashScreen : AppCompatActivity() {
    private val sharedPreferenceHelper :SharedPreferenceHelper by inject()
    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        handler=Handler()
    handler.postDelayed({
        val intent:Intent = if(sharedPreferenceHelper.getUser()==null)
            Intent(this, LoginSignUpActivity::class.java)
        else Intent(this,DashBoardActivity::class.java)
        startActivity(intent)
        finish()
    },3000)
    }
}