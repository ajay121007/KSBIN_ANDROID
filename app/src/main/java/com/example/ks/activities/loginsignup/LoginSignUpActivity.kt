package com.example.ks.activities.loginsignup

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.ks.LogoutWorker
import com.example.ks.R
import com.example.ks.adapters.ViewPagerAdapter
import com.example.ks.common.BaseActivity
import com.example.ks.databinding.ActivityLoginSignUpBinding
import com.example.ks.fragments.LoginFragment
import com.example.ks.fragments.SignUpFragment
import com.example.ks.sharedpref.SharedPreferenceHelper
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.android.ext.android.inject
import java.util.*


class LoginSignUpActivity : BaseActivity(), ViewPager.OnPageChangeListener {
    val sharedPreferenceHelper:SharedPreferenceHelper by inject()

    lateinit var activityLoginSignUpBinding: ActivityLoginSignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login_sign_up)

        val uploadWorkRequest: WorkRequest =
            OneTimeWorkRequestBuilder<LogoutWorker>()
                .build()
        WorkManager
            .getInstance(this)
            .enqueue(uploadWorkRequest)
      FirebaseMessaging.getInstance().token.addOnCompleteListener {
          Log.i(this.javaClass.simpleName, "token : ${it.result} ")
      }
        Log.i(this.javaClass.simpleName, "device id ${ UUID.randomUUID().toString()}: ")
//        if(sharedPreferenceHelper.getUser()!=null)baseViewModel.logoutUser()
        activityLoginSignUpBinding=DataBindingUtil.setContentView(
            this,
            R.layout.activity_login_sign_up
        )
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

    override fun onDestroy() {

        super.onDestroy()

    }
}