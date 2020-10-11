package com.example.ks.activities.claim

import android.net.Uri
import com.example.ks.common.UICallBacks
import com.example.ks.repo.AuthRepo
import com.example.ks.utils.MyViewModel
import com.example.ks.utils.ResultWrapper
import com.example.ks.utils.SingleLiveEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class FileClaimViewModel (override val uiCallBacks: UICallBacks, val authRepo: AuthRepo) : MyViewModel(
    uiCallBacks
){

    val onData=SingleLiveEvent<String>()
    fun uploadDocs(filePath: String, fileName: String){
        val url = Uri.parse(filePath.toString())

        val file = File(url.path)
        val requestFile2 =
            RequestBody.create("*/*".toMediaTypeOrNull(), file)
        val  filePath = MultipartBody.Part.createFormData(
            "file", file.name,
            requestFile2
        )

        val fileName = MultipartBody.Part.createFormData("file_name", fileName)
        uiCallBacks.onLoading(true)
        GlobalScope.launch {
            when(val response=authRepo.uploadClaims(filePath, fileName)){

                is ResultWrapper.Success -> {
                    uiCallBacks.onLoading(false)
                    val data = response.value ?: return@launch
                    if (data.code == 200)
                    {
                        onData.postValue(data.data.token)
                        uiCallBacks.onToast(data.message)
                    }
                    else uiCallBacks.onToast(data.message)
                }
                is ResultWrapper.GenericError -> {
                    uiCallBacks.onLoading(false)
                    uiCallBacks.showDialog(response.error?.error_des)
                }
                ResultWrapper.SocketTimeOutError -> {
                    uiCallBacks.showDialog("No Internet Connection")
                }
                ResultWrapper.NetworkError -> {
                    uiCallBacks.showDialog("Something went wrong")
                }

            }


        }
    }

}