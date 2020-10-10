package com.example.ks.activities.payment



import android.content.Intent
import android.os.Bundle
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

class PaymentActivity : BaseActivity(), OnPaymentItemClick {

    lateinit var binding: ActivityPaymentBinding

    val viewModel: PaymentViewModel by viewModel { parametersOf(this) }
    lateinit var paymentAdapter:PaymentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment)
        initView()
        bindOberver()
    }

    private fun bindOberver() {
        paymentAdapter= PaymentAdapter(this)
        viewModel.liveData.observe(this, Observer {
            binding.rv.adapter=paymentAdapter
            paymentAdapter.submitList(it)
        })
        viewModel.onNavigate.observe(this, Observer {
            UserConstants.signWebUrl= "https://ksbin.sarwara.com/authorize-token/"+it
            startActivityForResult(Intent(this, WebViewActivity::class.java),101)
        })
    }

    private fun initView() {
        viewModel.getInvoiceList()
    }

    override fun onClick(model: PaymentResponse.PaymentModel) {
        onToast(model.id.toString())
        if(model.paid==0){
        viewModel.createPaymentToken(model.id)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.getInvoiceList()
    }
}