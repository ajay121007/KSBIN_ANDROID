package com.example.ks.activities.signabledocument

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.ks.R
import com.example.ks.activities.loginsignup.LoginViewModel
import com.example.ks.databinding.ActivitySignableDocumentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SignableDocumentActivity : AppCompatActivity() {

    val viewModel: SignableDocumentModel by viewModel { parametersOf(this) }
    lateinit var binding: ActivitySignableDocumentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signable_document)
        initView()
    }

    private fun initView() {
        viewModel.getContractList()
    }
}