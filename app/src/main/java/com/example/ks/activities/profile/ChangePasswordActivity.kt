package com.example.ks.activities.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil

import com.example.ks.R
import com.example.ks.activities.editprofile.EditProfileViewModel
import com.example.ks.common.BaseActivity
import com.example.ks.databinding.ActivityChangePasswordBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class ChangePasswordActivity : BaseActivity() {
    private val editProfileViewModel:EditProfileViewModel by viewModel { parametersOf(this) }
    lateinit var activityChangePasswordBinding: ActivityChangePasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityChangePasswordBinding=DataBindingUtil.setContentView(this, R.layout.activity_change_password)
        activityChangePasswordBinding.apply {
            model=editProfileViewModel
            lifecycleOwner=this@ChangePasswordActivity
            executePendingBindings()
        }
    }
}