package com.example.ks.repo

import com.example.ks.api.ApiService
import com.example.ks.models.LoginResponse
import com.example.ks.models.SignUpResponse
import com.example.ks.sharedpref.SharedPreferenceHelper
import com.example.ks.utils.ResultWrapper
import com.example.ks.utils.safeApiCall

/**
 * Created by skycap.
 */
class AuthRepo(private val apiService: ApiService
               ,private val sharedPreferenceHelper: SharedPreferenceHelper){
    suspend fun signUp(body:HashMap<String,String?>): ResultWrapper<SignUpResponse?> {
        return safeApiCall { apiService.signUp(body) }
    }

    suspend fun login(body:HashMap<String,String?>): ResultWrapper<LoginResponse?> {
        return when(val call = safeApiCall { apiService.login(body) }){
            is ResultWrapper.Success ->{
                sharedPreferenceHelper.saveUser(call.value)
                ResultWrapper.Success(call.value)
            }
            is ResultWrapper.GenericError -> ResultWrapper.GenericError()
            ResultWrapper.SocketTimeOutError -> ResultWrapper.SocketTimeOutError
            ResultWrapper.NetworkError -> ResultWrapper.NetworkError
        }
    }
}