


package com.example.ks.activities.payment

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

import com.example.ks.common.UICallBacks
import com.example.ks.repo.AuthRepo
import com.example.ks.utils.MyViewModel
import com.example.ks.utils.ResultWrapper
import com.example.ks.utils.SingleLiveEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PaymentViewModel (override val uiCallBacks: UICallBacks, val authRepo: AuthRepo) : MyViewModel(uiCallBacks){
     val searchQuery=MutableLiveData<String>()
    private val list=ArrayList<PaymentResponse.PaymentModel>()
    val liveData=MediatorLiveData<List<PaymentResponse.PaymentModel>>()
    val onData=SingleLiveEvent<String>()
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
                        uiCallBacks.onToast(data.message)
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

}