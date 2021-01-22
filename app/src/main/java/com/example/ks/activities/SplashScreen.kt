package com.example.ks.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.ks.R
import com.example.ks.activities.dashboard.DashBoardActivity
import com.example.ks.activities.loginsignup.LoginSignUpActivity
import com.example.ks.activities.profile.ProfileViewModel
import com.example.ks.common.BaseActivity
import com.example.ks.sharedpref.SharedPreferenceHelper
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SplashScreen : BaseActivity() {
    private val sharedPreferenceHelper:SharedPreferenceHelper by inject()
    private val profileViewModel:ProfileViewModel by viewModel { parametersOf(this)  }

    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.splash_screen)


        if(sharedPreferenceHelper.getUser()!=null)
        {
            profileViewModel.silentLogOut()
        }
        val intent=Intent(this, LoginSignUpActivity::class.java)
        startActivity(intent)

//        Intent(this, DashBoardActivity::class.java)
//        else
//            val intent=Intent(this, LoginSignUpActivity::class.java)
//        startActivity(intent)
    }
}