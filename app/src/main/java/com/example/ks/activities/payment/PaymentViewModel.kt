


package com.example.ks.activities.payment

import androidx.lifecycle.MutableLiveData

import com.example.ks.common.UICallBacks
import com.example.ks.repo.AuthRepo
import com.example.ks.utils.MyViewModel
import com.example.ks.utils.ResultWrapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PaymentViewModel (override val uiCallBacks: UICallBacks, val authRepo: AuthRepo) : MyViewModel(uiCallBacks){
    val liveData=MutableLiveData<List<PaymentResponse.PaymentModel>>()
    fun getInvoiceList(){
        uiCallBacks.onLoading(true)
        GlobalScope.launch {
            when(val response=authRepo.getInvoice()){

                is ResultWrapper.Success -> {
                    uiCallBacks.onLoading(false)
                    val data=response.value?:return@launch
                    if(data.code==200){
                        uiCallBacks.onToast(data.message)
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

}