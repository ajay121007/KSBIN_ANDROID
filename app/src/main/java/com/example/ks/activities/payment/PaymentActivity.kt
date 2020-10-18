package com.example.ks.activities.payment



import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.ks.R
import com.example.ks.activities.webview.WebViewActivity
import com.example.ks.adapters.OnPaymentItemClick
import com.example.ks.adapters.PaymentAdapter
import com.example.ks.common.BaseActivity
import com.example.ks.constants.UserConstants
import com.example.ks.databinding.ActivityPaymentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PaymentActivity : BaseActivity(), OnPaymentItemClick, SearchView.OnQueryTextListener {

    lateinit var binding: ActivityPaymentBinding

    val viewModel: PaymentViewModel by viewModel { parametersOf(this) }
    lateinit var paymentAdapter:PaymentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment)
        binding.apply {


        }
        setSupportActionBar(binding.toolbar)
        binding.executePendingBindings()
        initView()
        bindOberver()
    }

    private fun bindOberver() {
        paymentAdapter= PaymentAdapter(this)
        viewModel.liveData.observe(this, Observer {
            binding.rv.adapter=paymentAdapter
            paymentAdapter.submitList(it)
        })
        viewModel.onData.observe(this, Observer {
            startActivityForResult(Intent(this, WebViewActivity::class.java).apply {
                putExtra("token",it)
                putExtra("title","Payment")
            },101)
        })
    }

    private fun initView() {
        viewModel.getInvoiceList()
    }

    override fun onClick(model: PaymentResponse.PaymentModel) {
//        onToast(model.id.toString())
        if(model.paid==0){
        viewModel.createPaymentToken(model.id)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.getInvoiceList()
        if(resultCode== RESULT_OK){
            onToast("Payment Successful")
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
}