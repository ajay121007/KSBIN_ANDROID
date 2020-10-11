package com.example.ks.activities.loginsignup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.example.ks.R
import com.example.ks.adapters.ViewPagerAdapter
import com.example.ks.databinding.ActivityLoginSignUpBinding
import com.example.ks.fragments.SignUpFragment
import com.example.ks.fragments.LoginFragment
import java.lang.RuntimeException


class LoginSignUpActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

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
            adapter.addFragments(LoginFragment(), "Sign In")
            adapter.addFragments(SignUpFragment(), "Sign Up")
            activityLoginSignUpBinding.viewPager.adapter=adapter
            activityLoginSignUpBinding.tabLayout.setupWithViewPager(activityLoginSignUpBinding.viewPager)
            activityLoginSignUpBinding.viewPager.addOnPageChangeListener(this)
        }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        if(position==0){
            activityLoginSignUpBinding.apply {
                title.text=getString(R.string.welcome_to_n_k_s_billing_associates)
                subTitle.text=getString(R.string.sign_in_to_continue)
            }
        }
        else{
            activityLoginSignUpBinding.apply {
                title.text=getString(R.string.create_new_account)
                subTitle.text=getString(R.string.sign_up_to_continue)
            }
        }
    }


    override fun onPageScrollStateChanged(state: Int) {

    }
}