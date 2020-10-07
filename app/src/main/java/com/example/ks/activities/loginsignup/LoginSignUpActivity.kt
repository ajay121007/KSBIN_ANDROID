package com.example.ks.activities.loginsignup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.ks.R
import com.example.ks.adapters.ViewPagerAdapter
import com.example.ks.databinding.ActivityLoginSignUpBinding
import com.example.ks.fragments.SignUpFragment
import com.example.ks.fragments.LoginFragment


class LoginSignUpActivity : AppCompatActivity() {

    lateinit var activityLoginSignUpBinding: ActivityLoginSignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login_sign_up)
        activityLoginSignUpBinding=DataBindingUtil.setContentView(this,R.layout.activity_login_sign_up)
        activityLoginSignUpBinding.executePendingBindings()
        setUpTabs()
    }

        private fun setUpTabs(){
            val adapter=ViewPagerAdapter(supportFragmentManager)
            adapter.addFragments(SignUpFragment(), "Login")
            adapter.addFragments(LoginFragment(), "SignUp")
            activityLoginSignUpBinding.viewPager.adapter=adapter
            activityLoginSignUpBinding.tabLayout.setupWithViewPager(activityLoginSignUpBinding.viewPager)
        }
}