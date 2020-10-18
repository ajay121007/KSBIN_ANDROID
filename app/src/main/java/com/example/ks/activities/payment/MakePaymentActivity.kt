package com.example.ks.activities.payment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil

import com.example.ks.R
import com.example.ks.api.Constants
import com.example.ks.common.BaseActivity
import com.example.ks.databinding.ActivityMakePaymentBinding

import net.authorize.acceptsdk.AcceptSDKApiClient
import net.authorize.acceptsdk.datamodel.merchant.ClientKeyBasedMerchantAuthentication
import net.authorize.acceptsdk.datamodel.transaction.CardData
import net.authorize.acceptsdk.datamodel.transaction.TransactionObject
import net.authorize.acceptsdk.datamodel.transaction.TransactionType
import net.authorize.acceptsdk.datamodel.transaction.callbacks.EncryptTransactionCallback
import net.authorize.acceptsdk.datamodel.transaction.response.EncryptTransactionResponse
import net.authorize.acceptsdk.datamodel.transaction.response.ErrorTransactionResponse
import net.authorize.acceptsdk.parser.JSONConstants
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class MakePaymentActivity : BaseActivity(), EncryptTransactionCallback {
    private val paymentViewModel:PaymentViewModel by viewModel {  parametersOf(this)}
    lateinit var activityMakePaymentBinding: ActivityMakePaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_make_payment)
        activityMakePaymentBinding=DataBindingUtil.setContentView(this,R.layout.activity_make_payment)
        activityMakePaymentBinding.model=paymentViewModel
        activityMakePaymentBinding.apply {
            setSupportActionBar(toolbarMake)
        }
        paymentViewModel.setPaymentCallback(this)
        val apiClient = AcceptSDKApiClient.Builder(
            this,
            AcceptSDKApiClient.Environment.SANDBOX
        )
            .connectionTimeout(5000) // optional connection time out in milliseconds
            .build()

        val cardData = CardData.Builder(
            "370000000000002",
            "06",  // MM
            "2021"
        ) // YYYY
            .cvvCode("900") // Optional
            .zipCode("46225") // Op
            // tional
            .cardHolderName(JSONConstants.Card.CARD_HOLDER_NAME) // Optional
            .build()
//        val merchantAuthenticationType = ClientKeyBasedMerchantAuthentication()
        val merchantAuthentication =
            ClientKeyBasedMerchantAuthentication.createMerchantAuthentication(
                Constants.API_KEY,
                Constants.CLIENT_KEY
            )
        val transactionObject =
            TransactionObject.createTransactionObject(TransactionType.SDK_TRANSACTION_ENCRYPTION) // type of transaction object
                .cardData(cardData)
                // card data to be encrypted
                .merchantAuthentication(merchantAuthentication) //Merchant authentication
                .build()
//        val tokenWithRequest = apiClient.getTokenWithRequest(transactionObject, this,)
//        ApiOperationBase.setEnvironment(Environment.SANDBOX);
//        val merchantAuthenticationType = MerchantAuthenticationType()
//        merchantAuthenticationType.setName("YOUR_API_LOGIN_ID")
//        merchantAuthenticationType.setTransactionKey("YOUR_TRANSACTION_KEY")
//        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType)
//        val run = CreateAnAndroidPayTransaction.run("47H9bGd6", "4zeh6Jb468e9CRpy")
    }

    override fun onErrorReceived(error: ErrorTransactionResponse?) {
        Log.i(this.javaClass.simpleName, ": ")
    }

    override fun onEncryptionFinished(response: EncryptTransactionResponse?) {
        Log.i(this.javaClass.simpleName, ":${response?.dataValue} ")
    }

    fun onPay(view: View) {
        val type=intent.getStringExtra("type")
        val id=intent.getStringExtra("id")
        paymentViewModel.makeCardPayment(id,type)

    }


}