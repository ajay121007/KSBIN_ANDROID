package com.example.ks.activities.editprofile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.ks.common.UICallBacks
import com.example.ks.repo.AuthRepo
import com.example.ks.utils.MyViewModel
import com.example.ks.utils.ResultWrapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class EditProfileViewModel (override val uiCallBacks: UICallBacks, val authRepo: AuthRepo) : MyViewModel(
    uiCallBacks
){
    val oldPassword=MutableLiveData<String>()
    val newPassword=MutableLiveData<String>()
    val confirmPassword=MutableLiveData<String>()

    val filePath=MutableLiveData<String>()
    val usernName=MutableLiveData<String>()
    val userMobile=MutableLiveData<String>()
    val userEmail=MutableLiveData<String>()
    fun updateProfile(){

        var uploadFilePath: MultipartBody.Part?=null
        if (!filePath.value.isNullOrEmpty()){
            val file = File(filePath.value?:return)
            val requestFile2 = file.asRequestBody("*//*".toMediaTypeOrNull())
            uploadFilePath = MultipartBody.Part.createFormData(
                "user_image", file.name,
                requestFile2
            )
        }
//        else {
//            val url = Uri.parse(filePath.toString())
//            val file = File(url.path)
//            val requestFile2 = RequestBody.create("*/*".toMediaTypeOrNull(), file)
//            uploadFilePath = MultipartBody.Part.createFormData(
//                "user_image", file.name,
//                requestFile2
//            )
//        }

        val name = usernName.value?.let { MultipartBody.Part.createFormData("name", it) }
        val phone = userMobile.value?.let { MultipartBody.Part.createFormData("mobile", it) }
        val email = userEmail.value?.let { MultipartBody.Part.createFormData("email", it) }


        uiCallBacks.onLoading(true)
        GlobalScope.launch {
            when(val response=authRepo.updateProfile(uploadFilePath, name, phone,email)){

                is ResultWrapper.Success -> {
                    uiCallBacks.onLoading(false)
                    val data = response.value ?: return@launch
                    if (data.code == 200)
                        uiCallBacks.showDialog("Profile updated successfully")
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

    fun updatedPassword(){
        when {
            oldPassword.value.isNullOrEmpty() -> {
                uiCallBacks.onToast("Please enter old password")
                return
            }
            newPassword.value.isNullOrEmpty() -> {
                uiCallBacks.onToast("Please enter new password")
                return
            }
            confirmPassword.value.isNullOrEmpty() -> {
                uiCallBacks.onToast("Please enter confirm password")
                return
            }
            confirmPassword.value!=newPassword.value -> {
                uiCallBacks.onToast("Password mismatched")
                return
            }
            else -> {
                val map = HashMap<String, String?>().apply {
                    put("current_password",oldPassword.value)
                    put("password",newPassword.value)
                    put("password_confirmation",confirmPassword.value)
                }
                uiCallBacks.onLoading(true)
                coroutineScope.launch {

                    when (val response=authRepo.updatePassword(map)) {
                        is ResultWrapper.Success -> {
                            uiCallBacks.onLoading(false)
                            if(response.value?.code==200){
                                uiCallBacks.showDialog(response.value.message)
                            }
                            else{
                                uiCallBacks.onToast(response.value?.message)
                            }
                        }
                        is ResultWrapper.GenericError -> {
                            uiCallBacks.onLoading(false)
                            uiCallBacks.onToast(response.error?.error_des)
                        }
                        ResultWrapper.SocketTimeOutError -> {
                            uiCallBacks.onLoading(false)
                            uiCallBacks.onToast(response.toString())
                        }
                        ResultWrapper.NetworkError -> {
                            uiCallBacks.onLoading(false)
                            uiCallBacks.onToast("Something went wrong")
                        }
                    }
                }
            }
        }
    }

}