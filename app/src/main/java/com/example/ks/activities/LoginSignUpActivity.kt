package com.example.ks.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ks.R
import com.example.ks.adapters.ViewPagerAdapter
import com.example.ks.fragments.LoginFragment
import com.example.ks.fragments.SignUpFragment
import kotlinx.android.synthetic.main.activity_login_sign_up.*

class LoginSignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_sign_up)
        setUpTabs()
    }

        private fun setUpTabs(){
            val adapter=ViewPagerAdapter(supportFragmentManager)
            adapter.addFragments(LoginFragment(), "Login")
            adapter.addFragments(SignUpFragment(), "SignUp")
            viewPager.adapter=adapter
            tab_layout.setupWithViewPager(viewPager)
        }
}