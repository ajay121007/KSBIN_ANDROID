package com.example.ks.activities.payment



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.ks.R
import com.example.ks.adapters.PaymentAdapter
import com.example.ks.common.BaseActivity
import com.example.ks.databinding.ActivityPaymentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PaymentActivity : BaseActivity() {

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
        paymentAdapter= PaymentAdapter()
        viewModel.liveData.observe(this, Observer {
            binding.rv.adapter=paymentAdapter
            paymentAdapter.submitList(it)
        })
    }

    private fun initView() {
      //  viewModel.getInvoiceList()
    }
}