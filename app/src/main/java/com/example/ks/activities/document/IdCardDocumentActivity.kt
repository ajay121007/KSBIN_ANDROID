package com.example.ks.activities.document

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.ks.R
import com.example.ks.databinding.ActivityIdCardDocumentBinding
import com.example.ks.databinding.ActivityPaymentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class IdCardDocumentActivity : AppCompatActivity() {
    lateinit var binding: ActivityIdCardDocumentBinding
    val viewModel: IdCardDocumentViewModel by viewModel { parametersOf(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_id_card_document)
        initView()
    }

    private fun initView() {
        viewModel.getDocumentOrIdsList()
    }
}