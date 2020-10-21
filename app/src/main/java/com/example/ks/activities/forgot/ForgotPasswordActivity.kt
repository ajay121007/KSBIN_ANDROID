package com.example.ks.activities.forgot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.ks.R
import com.example.ks.activities.dashboard.DashBoardActivity
import com.example.ks.activities.policydetail.ChangePolicyDetailViewModel
import com.example.ks.common.BaseActivity
import com.example.ks.databinding.ActivityChangePolicyDetials1Binding
import com.example.ks.databinding.ActivityForgotPasswordBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ForgotPasswordActivity : BaseActivity() {

    lateinit var binding: ActivityForgotPasswordBinding
    val viewModel: ForgotPasswordViewModel by viewModel { parametersOf(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)
        binding.viewModel=viewModel
        binding.apply {
            lifecycleOwner=this@ForgotPasswordActivity
            executePendingBindings()
        }
        initView()
    }

    private fun initView() {
        clickEvent()

    }

    private fun clickEvent() {

        binding.resetBtn.setOnClickListener {

            viewModel.forgotPAssword()
        }


    }
    override fun onToast(message: String?) {
        super.onToast(message)
//        finish()
    }
}