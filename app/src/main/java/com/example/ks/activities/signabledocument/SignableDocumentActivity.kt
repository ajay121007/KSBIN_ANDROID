package com.example.ks.activities.signabledocument

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.ks.R
import com.example.ks.activities.webview.WebViewActivity
import com.example.ks.adapters.ContractActions
import com.example.ks.adapters.OnContractItemClick
import com.example.ks.adapters.SignbaleAdapter
import com.example.ks.common.BaseActivity
import com.example.ks.constants.UserConstants
import com.example.ks.databinding.ActivitySignableDocumentBinding
import com.example.ks.model.contarctListResponse.ContractResponse
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SignableDocumentActivity : BaseActivity(), OnContractItemClick {

    val viewModel: SignableDocumentModel by viewModel { parametersOf(this) }
    lateinit var binding: ActivitySignableDocumentBinding
    lateinit var adapter:SignbaleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signable_document)
        initView()
    }

    private fun initView() {
        adapter= SignbaleAdapter(this)
        binding.rv.adapter=adapter
        viewModel.getContractList()
        viewModel.liveData.observe(this, Observer {
            adapter.submitList(it)
        })
        viewModel.signToken.observe(this, Observer {

            UserConstants.signWebUrl= "https://ksbin.sarwara.com/authorize-token/"+it
            startActivity(Intent(this,WebViewActivity::class.java))

        })
    }

    override fun onItemClick(actionType: ContractActions, item: ContractResponse.ContractModel) {
        when(actionType){
            ContractActions.SIGN -> viewModel.signToken(item.id)
            ContractActions.VIEW -> TODO()
        }
    }
}