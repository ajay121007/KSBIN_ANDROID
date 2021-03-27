


package com.example.ks.activities.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.ks.api.Constants

import com.example.ks.common.UICallBacks
import com.example.ks.repo.AuthRepo
import com.example.ks.utils.FieldValidators
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
import java.math.RoundingMode
import kotlin.math.roundToInt

class PaymentViewModel (override val uiCallBacks: UICallBacks,
                        val authRepo: AuthRepo,private val paymentApi: AcceptSDKApiClient
) : MyViewModel(uiCallBacks){
    lateinit var encryptTransactionObject: EncryptTransactionCallback
     val searchQuery=MutableLiveData<String>()
    private val list=ArrayList<PaymentResponse.PaymentModel>()
    val liveData=MediatorLiveData<List<PaymentResponse.PaymentModel>>()
    val onData=SingleLiveEvent<String>()

    val isCardSelected=MutableLiveData<Boolean>(true)
    // for making payments
    val cardHolderName=MutableLiveData<String>()
    val cardNumber=MutableLiveData<String>()
    val cardExpiry=MutableLiveData<String>()
    val cardCvv=MutableLiveData<String>()
    val cardPostal=MutableLiveData<String>()



    // bank payments
    val bankRoutingNumber=MutableLiveData<String>()
    val bankAcNumber=MutableLiveData<String>()
    val policyNumber=MutableLiveData<String>()


    val amount=MutableLiveData<Float>()

    val fee:LiveData<Double> = Transformations.map(isCardSelected){
        (if(it) {
//                val amt=amount.value?.toInt()?:0
//             (amt/100)*3.5
//            amount.value?.toDouble()
            amount.value?.let { it1 -> (3.50).times(it1).div(100) }?.toDouble()
        } else {
            3.00
        }) ?.toDouble()
    }

    val totalAmount:LiveData<Float?> = Transformations.map(fee){fees->
//        "%.2f".format( amount.value?.plus(3.0)).toFloat()
        "%.2f".format( amount.value?.plus(fees.toFloat())).toFloat()
    }
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

    fun setPaymentCallback(callBack:EncryptTransactionCallback){
        this.encryptTransactionObject=callBack
    }
    fun makeCardPayment(id:String?,type:String?,paymentOptions:String?,cardToken:String?){

        val map=HashMap<String,String?>().apply {
            put("customer_name",cardHolderName.value)

        }
        if(isCardSelected.value==true){
            map.put("zip_code",cardPostal.value)
            map.put("card_name",cardHolderName.value)
            map.put("expiration_month", cardExpiry.value?.split("/")?.get(0))
            map.put("expiration_year",cardExpiry.value?.split("/")?.get(1))
            map.put("card_token",cardToken)
            map.put("card_number", cardNumber.value?.length?.let { cardNumber.value?.substring(it-4,it) })
        }
        else{
            map["account_holder"]=cardHolderName.value
            map["routing_number"]=bankRoutingNumber.value
            map["account_number"]=bankAcNumber.value
        }
        if(type==Constants.INVOICE_PAYMENT){
            map["notes"]="Invoice $paymentOptions"
            map["type"] = "1"
            map["invoice_id"] = id
        }
        else{
            map["notes"]="Renewal"
            map["type"] = "2"
            map["renewal_id"] = id
            map["payment_option"] = paymentOptions
        }
           coroutineScope.launch {
               uiCallBacks.onLoading(true)
               if(isCardSelected.value==true)
               when(val response=authRepo.cardPayment(map)){
                   is ResultWrapper.Success -> {
                       uiCallBacks.onLoading(false)
                       if(response.value?.valid==true)
                       uiCallBacks.showDialogDownload(response.value?.message,response.value?.data?.invoiceUrl)
                       else  uiCallBacks.showDialog(response.value?.message)
                   }
                   is ResultWrapper.GenericError -> {
                       uiCallBacks.onLoading(false)
                       uiCallBacks.showDialog("Something went wrong")
                   }
                   ResultWrapper.SocketTimeOutError -> {
                       uiCallBacks.onLoading(false)
                       uiCallBacks.showDialog("Something went wrong")
                   }
                   ResultWrapper.NetworkError -> {
                       uiCallBacks.onLoading(false)
                       uiCallBacks.showDialog("Something went wrong")
                   }
               }
               else {
                   when(val response=authRepo.bankPayment(map)){
                       is ResultWrapper.Success -> {
                           uiCallBacks.onLoading(false)
                           if(response.value?.valid==true)
                               uiCallBacks.showDialogDownload(response.value?.message,response.value?.data?.invoiceUrl)
                           else  uiCallBacks.showDialog(response.value?.message)
                       }
                       is ResultWrapper.GenericError -> {
                           uiCallBacks.onLoading(false)
                           uiCallBacks.showDialog("Something went wrong")
                       }
                       ResultWrapper.SocketTimeOutError -> {
                           uiCallBacks.onLoading(false)
                           uiCallBacks.showDialog("Something went wrong")}
                       ResultWrapper.NetworkError -> {
                           uiCallBacks.onLoading(false)
                           uiCallBacks.showDialog("Something went wrong")}
                       }
                   }

               }


           }

     fun validateData() :Boolean{
        if(isCardSelected.value==true){
           return when {
                cardHolderName.value.isNullOrEmpty() -> {
                    uiCallBacks.onToast("PLease enter valid name")
                    false
                }
                cardNumber.value.isNullOrEmpty() -> {
                    uiCallBacks.onToast("Card number must not be empty")
                    false
                }
                cardExpiry.value.isNullOrEmpty() -> {
                    uiCallBacks.onToast("Card Expiry must not be empty")
                    false
                }
                cardCvv.value.isNullOrEmpty() -> {
                    uiCallBacks.onToast("Card CVV must not be empty")
                    false
                }
                cardPostal.value.isNullOrEmpty() -> {
                    uiCallBacks.onToast("Postal code must not be empty")
                    false
                }
               else -> true
            }
        }
        else{
            return when{
                cardHolderName.value.isNullOrEmpty() -> {
                    uiCallBacks.onToast("PLease enter valid name")
                    false
                }
                bankRoutingNumber.value.isNullOrEmpty()->{
                    uiCallBacks.onToast("Routing number must not be empty")
                    false
                }
                bankAcNumber.value.isNullOrEmpty()->{
                    uiCallBacks.onToast("Account number must not be empty")
                    false
                }
                else -> true
            }
        }
    }


    fun makeBankPayment(id:String?, type:String?,policy:String?,cardToken:String?){
//        val map=HashMap<String,String?>().apply {
//            put("notes",)
//            put("customer_name",)
//
//            put("card_name",)
//            put("expiration_month",)
//            put("expiration_year",)
//            put("zip_code",)
//            put("card_token",cardToken)
//            put("card_number",)
//        }
//        if(type==Constants.INVOICE_PAYMENT){
//            map["type"] = "1"
//            map["invoice_id"] = id
//        }
//        else{
//            map["type"] = "2"
//            map["renewal_id"] = id
//        }
//        coroutineScope.launch {
//            when(authRepo.bankPayment(map)){
//                is ResultWrapper.Success -> TODO()
//                is ResultWrapper.GenericError -> TODO()
//                ResultWrapper.SocketTimeOutError -> TODO()
//                ResultWrapper.NetworkError -> TODO()
//            }
//        }
    }

    fun generateToken(){
        if(cardNumber.value?.isNotEmpty()==true&&cardExpiry.value?.isNotEmpty()==true&&cardCvv.value?.isNotEmpty()==true)
        {
            uiCallBacks.onLoading(true)
            val cardData = CardData.Builder(
                cardNumber.value,
                cardExpiry.value?.substring(0,2),  // MM
                "20${cardExpiry.value?.substring(3,5)}"
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
    fun onTapBank(){
        isCardSelected.postValue(false)
    }

    fun onTapCard(){
        isCardSelected.postValue(true)
    }
}


