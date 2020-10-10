package com.example.ks.activities.signabledocument

import android.content.Intent
import android.net.Uri
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
import com.example.ks.model.documentid.DocumentModel
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

    override fun onResume() {
        super.onResume()
        viewModel.getContractList()
    }
    private fun initView() {
        adapter= SignbaleAdapter(this)
        binding.rv.adapter=adapter

        viewModel.liveData.observe(this, Observer {
            adapter.submitList(it)
        })
        viewModel.signToken.observe(this, Observer {

            UserConstants.signWebUrl = "https://ksbin.sarwara.com/authorize-token/" + it
            startActivity(Intent(this, WebViewActivity::class.java))

        })
    }

    override fun onItemClick(actionType: ContractActions, item: DocumentModel) {
        when(actionType){
            ContractActions.SIGN -> viewModel.signToken(item.id.toString())
            ContractActions.VIEW -> {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.invoice_url))
                startActivity(browserIntent)
            }
        }
    }
}