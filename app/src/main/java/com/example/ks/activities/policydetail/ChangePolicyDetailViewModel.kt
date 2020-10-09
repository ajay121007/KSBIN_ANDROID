package com.example.ks.activities.policydetail

import androidx.lifecycle.MutableLiveData
import com.example.ks.activities.payment.PaymentResponse
import com.example.ks.common.UICallBacks
import com.example.ks.repo.AuthRepo
import com.example.ks.repo.UserRepo
import com.example.ks.utils.MyViewModel
import com.example.ks.utils.ResultWrapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import skycap.android.core.livedata.SingleEventLiveData

class ChangePolicyDetailViewModel(override val uiCallBacks: UICallBacks, val authRepo: AuthRepo) : MyViewModel(uiCallBacks){
    val liveData= SingleEventLiveData<String>()
    fun updatePolicy(policyNumber:String, details:String, subject:String){
        val map=HashMap<String,String?>().apply {
            put("policy",policyNumber)
            put("details",details)
            put("subject",subject)
        }
        uiCallBacks.onLoading(true)
        GlobalScope.launch {
            when(val response=authRepo.policyUpdate(map)){

                is ResultWrapper.Success -> {
                    uiCallBacks.onLoading(false)
                    val data=response.value?:return@launch
                    if(data.code==200){
                        uiCallBacks.onToast(data.message)
                        liveData.postValue(response.value.message)
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