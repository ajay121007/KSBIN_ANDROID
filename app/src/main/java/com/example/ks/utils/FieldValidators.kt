package com.example.ks.utils

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


/**
 * Created by skycap.
 */
object FieldValidators {
     fun setEmailError(text: String): LiveData<String>? {
        val liveData = MutableLiveData<String>()
        if(!TextUtils.isEmpty(text) && android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches().not())
            liveData.value="Enter valid email address"
        else liveData.value=null
        return liveData
    }

      fun setNotEmptyError(string: String): LiveData<String> {
        val liveData = MutableLiveData<String>()
        if(string.isEmpty()) liveData.value="Must not be empty"
        else liveData.value=null
        return liveData
    }
    fun setPhoneError(string: String): LiveData<String> {
        val liveData = MutableLiveData<String>()
        if(string.length<9||string.isEmpty()) liveData.value="Min length is 10"
        else liveData.value=null
        return liveData
    }
    fun setPassError(string: String): LiveData<String> {
        val liveData = MutableLiveData<String>()
        if(string.length<8||string.isEmpty()) liveData.value="Min length is 8"
        else liveData.value=null
        return liveData
    }
}
