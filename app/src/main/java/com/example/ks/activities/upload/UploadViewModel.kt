package com.example.ks.activities.upload

import android.net.Uri
import com.example.ks.common.UICallBacks
import com.example.ks.repo.AuthRepo
import com.example.ks.utils.MyViewModel
import com.example.ks.utils.ResultWrapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.net.URI.create


class UploadViewModel(override val uiCallBacks: UICallBacks, val authRepo: AuthRepo) : MyViewModel(
    uiCallBacks
){


    fun uploadDocs(filePath: String, fileName: String){

        val url = Uri.parse(filePath.toString())
        val file = File(url.path)
        val requestFile2 =
            RequestBody.create("*//*".toMediaTypeOrNull(), file)
       val  filePath = MultipartBody.Part.createFormData(
            "file", file.name,
            requestFile2
        )
        val fileName = MultipartBody.Part.createFormData("file_name", fileName)
        uiCallBacks.onLoading(true)
        GlobalScope.launch {
            when(val response=authRepo.uploadDocs(filePath, fileName)){

                is ResultWrapper.Success -> {
                    uiCallBacks.onLoading(false)
                    val data = response.value ?: return@launch
                    if (data.code == 200)
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