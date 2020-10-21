package com.example.ks.activities.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil

import com.example.ks.R
import com.example.ks.databinding.ActivityChangePasswordBinding


class ChangePasswordActivity : AppCompatActivity() {

    lateinit var activityChangePasswordBinding: ActivityChangePasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityChangePasswordBinding=DataBindingUtil.setContentView(this, R.layout.activity_change_password)

    }
}