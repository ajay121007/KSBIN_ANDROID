package com.example.ks.activities.signabledocument

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ks.common.UICallBacks
import com.example.ks.constants.UserConstants
import com.example.ks.model.contarctListResponse.ContractResponse
import com.example.ks.model.documentid.DocumentModel
import com.example.ks.models.DashBoardResponse
import com.example.ks.repo.AuthRepo
import com.example.ks.utils.MyViewModel
import com.example.ks.utils.ResultWrapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import skycap.android.core.livedata.SingleEventLiveData

class SignableDocumentModel(override val uiCallBacks: UICallBacks, val authRepo: AuthRepo) : MyViewModel(uiCallBacks){

    val liveData=MutableLiveData<List<DocumentModel?>>()
    val signToken=SingleEventLiveData<String>()
    fun getContractList(){
        uiCallBacks.onLoading(true)
        GlobalScope.launch {
            when(val response=authRepo.getDocumentIdList()){
                is ResultWrapper.Success -> {
                    uiCallBacks.onLoading(false)
                    val data=response.value?:return@launch
                    if(data.code==200)
                    {
                        uiCallBacks.onToast(data.message)
                        response.value.data.let {

                            liveData.postValue(it)
                        }
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
    fun signToken(contractId: String?): Unit {
        coroutineScope.launch {
            val map=HashMap<String,String?>().apply {
                put("contract_id",contractId)
            }
            uiCallBacks.onLoading(true)
            when(val response= authRepo.signContractToken(map)){
                is ResultWrapper.Success -> {
                    UserConstants.signToken= response.value?.data?.token.toString()
                    uiCallBacks.onToast(response.value?.data?.token)
                    uiCallBacks.onLoading(false)
                    signToken.postValue(response.value?.data?.token)
                }
                is ResultWrapper.GenericError -> TODO()
                ResultWrapper.SocketTimeOutError -> TODO()
                ResultWrapper.NetworkError -> TODO()
            }
        }
    }
}