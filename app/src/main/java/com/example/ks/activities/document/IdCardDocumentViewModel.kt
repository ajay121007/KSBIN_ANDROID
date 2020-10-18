package com.example.ks.activities.document

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.ks.common.UICallBacks
import com.example.ks.model.documentid.DocumentModel
import com.example.ks.repo.AuthRepo
import com.example.ks.utils.MyViewModel
import com.example.ks.utils.ResultWrapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class IdCardDocumentViewModel (override val uiCallBacks: UICallBacks, val authRepo: AuthRepo) : MyViewModel(uiCallBacks){
    val searchQuery=MutableLiveData<String>()
    private val list=ArrayList<DocumentModel>()
    val onData=MediatorLiveData<List<DocumentModel>>()
    init {
        onData.addSource(searchQuery){query->
            if(query.isNotEmpty())
            {
                onData.postValue(list.filter { it.name.contains(query,ignoreCase = true)}.toList())
            }
            else{
                onData.postValue(list)
            }
        }
    }
    fun getDocumentOrIdsList(){
        uiCallBacks.onLoading(true)
        GlobalScope.launch {
            when(val response=authRepo.getDocumentIdList()){

                is ResultWrapper.Success -> {
                    uiCallBacks.onLoading(false)
                    val data=response.value?:return@launch
                    if(data.code==200)
                    {
                        list.clear()
                        list.addAll(response.value.data)
                        onData.postValue(response.value.data)
//                        uiCallBacks.onToast(data.message)
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