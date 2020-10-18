


package com.example.ks.activities.payment

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.ks.api.Constants

import com.example.ks.common.UICallBacks
import com.example.ks.repo.AuthRepo
import com.example.ks.utils.MyViewModel
import com.example.ks.utils.ResultWrapper
import com.example.ks.utils.SingleLiveEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.authorize.acceptsdk.AcceptSDKApiClient
import net.authorize.acceptsdk.datamodel.merchant.ClientKeyBasedMerchantAuthentication
import net.authorize.acceptsdk.datamodel.transaction.CardData
import net.authorize.acceptsdk.datamodel.transaction.EncryptTransactionObject
import net.authorize.acceptsdk.datamodel.transaction.TransactionObject
import net.authorize.acceptsdk.datamodel.transaction.TransactionType
import net.authorize.acceptsdk.datamodel.transaction.callbacks.EncryptTransactionCallback
import net.authorize.acceptsdk.parser.JSONConstants

class PaymentViewModel (override val uiCallBacks: UICallBacks,
                        val authRepo: AuthRepo,private val paymentApi: AcceptSDKApiClient
) : MyViewModel(uiCallBacks){
    lateinit var encryptTransactionObject: EncryptTransactionCallback
     val searchQuery=MutableLiveData<String>()
    private val list=ArrayList<PaymentResponse.PaymentModel>()
    val liveData=MediatorLiveData<List<PaymentResponse.PaymentModel>>()
    val onData=SingleLiveEvent<String>()

    // for making payments
    val cardHolderName=MutableLiveData<String>()
    val cardNumber=MutableLiveData<String>()
    val cardExpiry=MutableLiveData<String>()
    val cardCvv=MutableLiveData<String>()
    val cardPostal=MutableLiveData<String>()
//    val cardHolderName=MutableLiveData<String>()
    init {
        liveData.addSource(searchQuery){query->
            if(query.isNotEmpty())
            {
                liveData.postValue(list.filter { it.invoiceNo.contains(query,ignoreCase = true)}.toList())
            }
            else{
                liveData.postValue(list)
            }
        }
    }
    fun getInvoiceList(){
        uiCallBacks.onLoading(true)
        GlobalScope.launch {
            when(val response=authRepo.getInvoice()){

                is ResultWrapper.Success -> {
                    uiCallBacks.onLoading(false)
                    val data=response.value?:return@launch
//                    uiCallBacks.showDialog(data.message)
                    if(data.code==200){
//                        uiCallBacks.onToast(data.message)
                        list.clear()
                        list.addAll(response.value.data)
                        liveData.postValue(response.value.data)
                    }

                    else uiCallBacks.onToast(data.message)
                }
                is ResultWrapper.GenericError -> {
                    uiCallBacks.onLoading(false)
                    uiCallBacks.onToast(response.error?.error_des)
                }
                ResultWrapper.SocketTimeOutError -> {

                }
                ResultWrapper.NetworkError -> {

                }

            }


        }
    }
    fun createPaymentToken(id: Int): Unit {
        uiCallBacks.onLoading(true)
        coroutineScope.launch {
            when(val response=authRepo.createPaymentToken(id)){
                is ResultWrapper.Success -> {
                    uiCallBacks.onLoading(false)
                    onData.postValue(response.value?.data?.token)
                }
                is ResultWrapper.GenericError -> {
                    uiCallBacks.onLoading(false)
                }
                ResultWrapper.SocketTimeOutError ->{
                    uiCallBacks.onLoading(false)
                }
                ResultWrapper.NetworkError -> {
                    uiCallBacks.onLoading(false)
                }
            }
        }
    }
    fun setPaymentCallback(callBack:EncryptTransactionCallback){
        this.encryptTransactionObject=callBack
    }
    fun makeCardPayment(id:String?, type:String?){
        if(cardNumber.value?.isNotEmpty()==true&&cardExpiry.value?.isNotEmpty()==true&&cardCvv.value?.isNotEmpty()==true)

        {
//            uiCallBacks.onLoading(true)
            val cardData = CardData.Builder(
                cardNumber.value,
                "06",  // MM
                "2021"
            ) // YYYY
                .cvvCode(cardCvv.value) // Optional
                .zipCode(cardPostal.value) // Op
                // tional
                .cardHolderName(cardHolderName.value) // Optional
                .build()

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
            val tokenWithRequest = paymentApi.getTokenWithRequest(transactionObject, encryptTransactionObject,)
        }
    }
}