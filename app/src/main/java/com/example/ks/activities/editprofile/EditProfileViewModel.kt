package com.example.ks.activities.editprofile

import android.net.Uri
import com.example.ks.common.UICallBacks
import com.example.ks.repo.AuthRepo
import com.example.ks.utils.MyViewModel
import com.example.ks.utils.ResultWrapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class EditProfileViewModel (override val uiCallBacks: UICallBacks, val authRepo: AuthRepo) : MyViewModel(
    uiCallBacks
){


    fun updateProfile(filePath: String, fileName: String,userName: String, userPhone:String, emailId:String){

        var uploadFilePath: MultipartBody.Part
        if (filePath.isEmpty()){
            uploadFilePath = MultipartBody.Part.createFormData("user_image", "")
        }
        else {
            val url = Uri.parse(filePath.toString())
            val file = File(url.path)
            val requestFile2 = RequestBody.create("*/*".toMediaTypeOrNull(), file)
            uploadFilePath = MultipartBody.Part.createFormData(
                "user_image", file.name,
                requestFile2
            )
        }

        val name = MultipartBody.Part.createFormData("name", userName)
        val phone = MultipartBody.Part.createFormData("mobile", userPhone)
        val email = MultipartBody.Part.createFormData("email", emailId)
        uiCallBacks.onLoading(true)
        GlobalScope.launch {
            when(val response=authRepo.updateProfile(uploadFilePath, name, phone,email)){

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