package com.example.ks.activities.renewal

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.ks.R
import com.example.ks.activities.RenewalActivity
import com.example.ks.activities.webview.WebViewActivity
import com.example.ks.adapters.OnCLickOptions
import com.example.ks.adapters.RenewalAdapter
import com.example.ks.adapters.RenewalOptions
import com.example.ks.common.BaseActivity
import com.example.ks.databinding.ActivityPaymentPlanBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PaymentPlanActivity : BaseActivity(), OnCLickOptions {
    private val renewalViewModel:RenewalViewModel by viewModel { parametersOf(this) }
    private lateinit var activityPaymentPlanBinding: ActivityPaymentPlanBinding
    lateinit var renewalAdapter: RenewalAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_renewal_list)
        activityPaymentPlanBinding=DataBindingUtil.setContentView(this,R.layout.activity_payment_plan)
        renewalAdapter= RenewalAdapter(this)
        activityPaymentPlanBinding.apply {
            recycleRenewal.adapter=renewalAdapter
            lifecycleOwner=this@PaymentPlanActivity
            executePendingBindings()
        }
        setSupportActionBar(activityPaymentPlanBinding.toolbar)
        renewalViewModel.getRenewalsList()
        activityPaymentPlanBinding.addRenewalBtn.setOnClickListener {
            startActivity(Intent(this,RenewalActivity::class.java))
        }
        renewalViewModel.data.observe(this, Observer {
//            onToast(it?.size.toString())
            renewalAdapter.submitList(it)
        })
        renewalViewModel.onToken.observe(this, Observer {
            startActivityForResult(Intent(this,WebViewActivity::class.java).apply {
                putExtra("token",it)
            },101)
        })
    }

    override fun onOptions1(id: Int, options: RenewalOptions) {
        when(options){
            RenewalOptions.OPTIONS1 -> renewalViewModel.renewDocuments(id,"options1")
            RenewalOptions.OPTIONS2 -> renewalViewModel.renewDocuments(id,"options12")
            RenewalOptions.PAY_IN_FULL -> renewalViewModel.renewDocuments(id,"payinfull")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        renewalViewModel.getRenewalsList()
        if(resultCode== RESULT_OK){
            onToast("Payment Successful")
        }
    }
}