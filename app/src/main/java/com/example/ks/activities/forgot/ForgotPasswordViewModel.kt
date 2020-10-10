package com.example.ks.activities.forgot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.ks.common.UICallBacks
import com.example.ks.repo.AuthRepo
import com.example.ks.utils.FieldValidators
import com.example.ks.utils.MyViewModel
import com.example.ks.utils.ResultWrapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import skycap.android.core.livedata.SingleEventLiveData

class ForgotPasswordViewModel (override val uiCallBacks: UICallBacks, val authRepo: AuthRepo) : MyViewModel(uiCallBacks){
    val liveData= SingleEventLiveData<String>()
    val email = MutableLiveData<String>()
    val emailError: LiveData<String> = Transformations.switchMap(email, FieldValidators::setEmailError)

    fun forgotPAssword(){
//        email:4mit.inc@gmail.com
//        password:12345678
//        device_type:1
//        device_token:apple
//        notification_token:mango
        val map=HashMap<String,String?>().apply {
            put("email",email.value)

        }
        uiCallBacks.onLoading(true)
        GlobalScope.launch {
            when(val response=authRepo.forgotPassword(map)){

                is ResultWrapper.Success -> {
                    uiCallBacks.onLoading(false)
                    val data=response.value?:return@launch
                    if(data.code==200)
                        uiCallBacks.onToast(data.message)
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