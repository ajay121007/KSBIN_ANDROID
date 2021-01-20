package com.example.ks.activities.renewal

import androidx.lifecycle.MutableLiveData
import com.example.ks.common.UICallBacks
import com.example.ks.model.renewals.RenewalResponse
import com.example.ks.repo.AuthRepo
import com.example.ks.utils.MyViewModel
import com.example.ks.utils.ResultWrapper
import com.example.ks.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * Created by skycap.
 */
class RenewalViewModel(override val uiCallBacks: UICallBacks, private val userRepo: AuthRepo) : MyViewModel(uiCallBacks){
    val data=MutableLiveData<List<RenewalResponse.RenewalModel?>>()
    val ddc=MutableLiveData<String>()
    val tcl=MutableLiveData<String>()
    val dmv=MutableLiveData<String>()
    val onToken=SingleLiveEvent<String>()

    fun getRenewalsList(){
    uiCallBacks.onLoading(true)
    coroutineScope.launch {
        when(val response=userRepo.getRenewalsList()){
            is ResultWrapper.Success -> {
                data.postValue(response.value?.data)
                uiCallBacks.onLoading(false)
            }
            is ResultWrapper.GenericError -> {
                uiCallBacks.onLoading(false)
            }
            ResultWrapper.SocketTimeOutError -> {
                    uiCallBacks.onLoading(false)
                }
            ResultWrapper.NetworkError -> {
                uiCallBacks.onLoading(false)
            }
        }
    }
}

    fun uploadRenewals(){
        if(dmv.value==null&&tcl.value==null&&ddc.value==null)return
        var dmvFile:MultipartBody.Part?=null
        var tclFile:MultipartBody.Part?=null
        var ddcFile:MultipartBody.Part?=null
        dmv.value?.let {
            val file = File(it)
            val requestFile2 = RequestBody.create("*/*".toMediaTypeOrNull(), file)
            dmvFile = MultipartBody.Part.createFormData(
                "dmv", file.name,
                requestFile2
            )
        }
        tcl.value?.let {
            val file = File(it)
            val requestFile2 = RequestBody.create("*/*".toMediaTypeOrNull(), file)
            tclFile = MultipartBody.Part.createFormData(
                "tcl", file.name,
                requestFile2
            )
        }
        ddc.value?.let {
            val file = File(it)
            val requestFile2 = RequestBody.create("*/*".toMediaTypeOrNull(), file)
            ddcFile = MultipartBody.Part.createFormData(
                "ddc", file.name,
                requestFile2
            )
        }
        coroutineScope.launch {
            uiCallBacks.onLoading(true)
            when(userRepo.uploadRenewals(dmvFile,tclFile,ddcFile)){
                is ResultWrapper.Success ->{
                    uiCallBacks.onLoading(false)
                    uiCallBacks.showDialog("Documents Uploaded Successfully")
                }
                is ResultWrapper.GenericError -> {}
                ResultWrapper.SocketTimeOutError -> {}
                ResultWrapper.NetworkError -> {}
            }
        }
    }

    fun renewDocuments(id:Int,type:String){
        uiCallBacks.onLoading(true)
        coroutineScope.launch {
            when(val response=userRepo.renewalDocument(id,type)){
                is ResultWrapper.Success -> {
                    uiCallBacks.onLoading(false)
                    onToken.postValue(response.value?.data?.token)
                }
                is ResultWrapper.GenericError ->  uiCallBacks.onLoading(false)
                ResultWrapper.SocketTimeOutError -> uiCallBacks.onLoading(false)
                ResultWrapper.NetworkError ->  uiCallBacks.onLoading(false)
            }
        }
    }
}