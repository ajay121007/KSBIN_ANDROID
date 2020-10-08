package com.example.ks.activities.payment


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.ks.R
import com.example.ks.activities.signabledocument.SignableDocumentModel
import com.example.ks.databinding.ActivityPaymentBinding
import com.example.ks.databinding.ActivitySignableDocumentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PaymentActivity : AppCompatActivity() {

    lateinit var binding: ActivityPaymentBinding
   // val viewModel: PaymentViewModel by viewModel { parametersOf(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment)
        initView()
    }

    private fun initView() {
      //  viewModel.getInvoiceList()
    }
}