package com.example.ks.activities.signabledocument

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.SearchView
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
import com.example.ks.models.DashBoardResponse
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SignableDocumentActivity : BaseActivity(), OnContractItemClick,
    androidx.appcompat.widget.SearchView.OnQueryTextListener {

    val viewModel: SignableDocumentModel by viewModel { parametersOf(this) }
    lateinit var binding: ActivitySignableDocumentBinding
    lateinit var adapter:SignbaleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signable_document)
        binding.viewModel=viewModel

        setSupportActionBar(binding.toolbarSignable1.apply {
        })
        binding.apply {
            searchView.onActionViewExpanded()
            searchView.setOnQueryTextListener(this@SignableDocumentActivity)
            lifecycleOwner=this@SignableDocumentActivity
            executePendingBindings()
            Handler().postDelayed({ searchView.clearFocus() }, 300)
        }

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


            startActivityForResult(Intent(this, WebViewActivity::class.java).apply {
                putExtra("token",it)
            },101)

        })
    }

    override fun onItemClick(actionType: ContractActions, item:ContractResponse.ContractModel) {
        when(actionType){
            ContractActions.SIGN -> {
                if(item.signed==0){
                    viewModel.signToken(item.id.toString())
                }
            }
            ContractActions.VIEW -> {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.contractUrl))
                startActivity(browserIntent)
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.searchQuery.postValue(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.searchQuery.postValue(newText)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK){
            onToast("Signed Successfully")
        }
    }
}