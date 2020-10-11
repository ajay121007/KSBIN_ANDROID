package com.example.ks.activities.document

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.ks.R
import com.example.ks.adapters.IdDocumentsAdapter
import com.example.ks.adapters.OnDocumentDownload
import com.example.ks.common.BaseActivity
import com.example.ks.databinding.ActivityIdCardDocumentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class IdCardDocumentActivity : BaseActivity(), OnDocumentDownload, SearchView.OnQueryTextListener {
    lateinit var binding: ActivityIdCardDocumentBinding
    val viewModel: IdCardDocumentViewModel by viewModel { parametersOf(this) }
    private val idDocumentsAdapter by lazy { IdDocumentsAdapter(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_id_card_document)
        setSupportActionBar(binding.toolbar.apply {
        })
        binding.apply {
            searchView.onActionViewExpanded()
            searchView.setOnQueryTextListener(this@IdCardDocumentActivity)
            Handler().postDelayed({ searchView.clearFocus() }, 300)
            executePendingBindings()
        }
        initView()
        viewModel.onData.observe(this, Observer {
            idDocumentsAdapter.submitList(it)
        })
    }

    private fun initView() {
        binding.rv.adapter=idDocumentsAdapter
        viewModel.getDocumentOrIdsList()
    }

    override fun onClick(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.searchQuery.postValue(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.searchQuery.postValue(newText)
        return true
    }
}