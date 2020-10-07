package com.example.ks.activities.loginsignup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.ks.common.UICallBacks
import com.example.ks.repo.AuthRepo
import com.example.ks.utils.FieldValidators
import com.example.ks.utils.MyViewModel
import com.example.ks.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

/**
 * Created by skycap.
 */
class LoginViewModel(override val uiCallBacks: UICallBacks,val authRepo: AuthRepo) :MyViewModel(uiCallBacks){

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val passwordError: LiveData<String> = Transformations.switchMap(password, FieldValidators::setPassError)
    val emailError: LiveData<String> = Transformations.switchMap(email, FieldValidators::setEmailError)

    fun login(){
//        email:4mit.inc@gmail.com
//        password:12345678
//        device_type:1
//        device_token:apple
//        notification_token:mango
        val map=HashMap<String,String?>().apply {
            put("email",email.value)
            put("password",password.value)
            put("device_type","1")
            put("device_token","apple")
            put("notification_token","mango")
        }
        uiCallBacks.onLoading(true)
        GlobalScope.launch {
            when(val response=authRepo.login(map)){

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