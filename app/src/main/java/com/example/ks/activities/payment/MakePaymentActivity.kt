package com.example.ks.activities.payment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import com.example.ks.R
import com.example.ks.api.Constants
import com.example.ks.common.BaseActivity
import com.example.ks.databinding.ActivityMakePaymentBinding
import com.google.gson.Gson

import net.authorize.acceptsdk.datamodel.transaction.callbacks.EncryptTransactionCallback
import net.authorize.acceptsdk.datamodel.transaction.response.EncryptTransactionResponse
import net.authorize.acceptsdk.datamodel.transaction.response.ErrorTransactionResponse
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class MakePaymentActivity : BaseActivity(), EncryptTransactionCallback {
    private val paymentViewModel:PaymentViewModel by viewModel {  parametersOf(this)}
    lateinit var activityMakePaymentBinding: ActivityMakePaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_make_payment)
        activityMakePaymentBinding=DataBindingUtil.setContentView(this,R.layout.activity_make_payment)
        paymentViewModel.setPaymentCallback(this)
        paymentViewModel.amount.value=intent.getFloatExtra("price",0.0f)
//        onToast(intent.getFloatExtra("price",0.0f).toString())
        if(intent.getStringExtra("type")==Constants.INVOICE_PAYMENT){
            val fromJson = Gson().fromJson(
                intent?.getStringExtra("model"),
                PaymentResponse.PaymentModel::class.java
            )
            paymentViewModel.policyNumber.value=fromJson.policy

        }
        else{
            activityMakePaymentBinding.layout2.tvPolicy.text="Notes"
            paymentViewModel.policyNumber.value="Renewal"
        }
        activityMakePaymentBinding.model=paymentViewModel
        activityMakePaymentBinding.apply {
            setSupportActionBar(toolbarMake)
            lifecycleOwner=this@MakePaymentActivity
            executePendingBindings()
        }
//        DebitBankAccount.run(Constants.API_KEY,"4zeh6Jb468e9CRpy",00.0)
        bindObservers()
    }

    private fun bindObservers() {
        paymentViewModel.isCardSelected.observe(this, Observer {
            if(it){
                activityMakePaymentBinding.apply {
                    layout1.root.visibility=View.VISIBLE
                    layout2.root.visibility=View.VISIBLE
                    bankLayout.root.visibility=View.GONE
                }
            }
            else{
                activityMakePaymentBinding.apply {
                    layout1.root.visibility=View.VISIBLE
                    layout2.root.visibility=View.VISIBLE
                    bankLayout.root.visibility=View.GONE
                }
            }
        })
        activityMakePaymentBinding.layout2.etExpiry.addTextChangedListener(object : TextWatcher {

            var sb : StringBuilder = StringBuilder("")

            var _ignore = false

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if(_ignore){
                    _ignore = false
                    return
                }

                sb.clear()
                sb.append(if(s!!.length > 10){ s.subSequence(0,10) }else{ s })

                if(sb.lastIndex == 2){
                    if(sb[2] != '/'){
                        sb.insert(2,"/")
                    }
                } else if(sb.lastIndex == 5){
                    if(sb[5] != '/'){
                        sb.insert(5,"/")
                    }
                }

                _ignore = true
                activityMakePaymentBinding.layout2.etExpiry.setText(sb.toString())
                activityMakePaymentBinding.layout2.etExpiry.setSelection(sb.length)

            }
        })
//        paymentViewModel.cardExpiry.observe(this, Observer {
//            if(it.length==2){
//                paymentViewModel.cardExpiry.value="$it/"
//            }
//            else if(it.length>2){
//                activityMakePaymentBinding.layout2.etExpiry.apply {
//                    setSelection(length())
//                }
//            }
//        })
    }

    override fun onErrorReceived(error: ErrorTransactionResponse?) {
        paymentViewModel.uiCallBacks.onLoading(false)
        Log.i(this.javaClass.simpleName, ": ${error?.firstErrorMessage}")
    }

    override fun onEncryptionFinished(response: EncryptTransactionResponse?) {
        paymentViewModel.uiCallBacks.onLoading(false)
        Log.i(this.javaClass.simpleName, ":${response?.dataValue} ")
        Log.i(this.javaClass.simpleName, ":${response?.dataDescriptor} ")
        val type=intent.getStringExtra("type")
        val id=intent.getStringExtra("id")
        val policy=intent.getStringExtra("policy")
        if(type==Constants.INVOICE_PAYMENT){
            val fromJson = Gson().fromJson(
                intent?.getStringExtra("model"),
                PaymentResponse.PaymentModel::class.java
            )
            if(paymentViewModel.validateData())
            paymentViewModel.makeCardPayment(fromJson.id.toString(),type,fromJson.policy,response?.dataValue)
        }

    }

    fun onPay(view: View) {
        if(paymentViewModel.isCardSelected.value==true&&paymentViewModel.validateData())
        paymentViewModel.generateToken()
        else{
            val type=intent.getStringExtra("type")
            val id=intent.getStringExtra("id")
            val options=intent.getStringExtra("options")

            if(type==Constants.INVOICE_PAYMENT){
                val fromJson = Gson().fromJson(
                    intent?.getStringExtra("model"),
                    PaymentResponse.PaymentModel::class.java
                )
                if(paymentViewModel.validateData())
                paymentViewModel.makeCardPayment(fromJson.id.toString(),type,fromJson.policy,null)
            }
            else{
                if(paymentViewModel.validateData())
                paymentViewModel.makeCardPayment(id.toString(),type,options,null)
            }
        }
    }


}