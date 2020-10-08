package com.example.ks.repo

import com.example.ks.api.ApiService
import com.example.ks.model.contarctListResponse.ContractListResponse
import com.example.ks.model.documentid.DocumentIdListResponse
import com.example.ks.model.invoice.InvoiceListResponse
import com.example.ks.model.profile.ProfileDetailResponse
import com.example.ks.models.LoginResponse
import com.example.ks.models.SignUpResponse
import com.example.ks.sharedpref.SharedPreferenceHelper
import com.example.ks.utils.ResultWrapper
import com.example.ks.utils.safeApiCall
import okhttp3.ResponseBody

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


    suspend fun getContractList(): ResultWrapper<ContractListResponse?> {
        return when(val call = safeApiCall { apiService.getContractList() }){
            is ResultWrapper.Success ->{
                ResultWrapper.Success(call.value)
            }
            is ResultWrapper.GenericError -> ResultWrapper.GenericError()
            ResultWrapper.SocketTimeOutError -> ResultWrapper.SocketTimeOutError
            ResultWrapper.NetworkError -> ResultWrapper.NetworkError
        }
    }

    suspend fun getInvoice(): ResultWrapper<InvoiceListResponse?> {
        return when(val call = safeApiCall { apiService.getInvoiceList() }){
            is ResultWrapper.Success ->{
                ResultWrapper.Success(call.value)
            }
            is ResultWrapper.GenericError -> ResultWrapper.GenericError()
            ResultWrapper.SocketTimeOutError -> ResultWrapper.SocketTimeOutError
            ResultWrapper.NetworkError -> ResultWrapper.NetworkError
        }
    }

    suspend fun getDocumentIdList(): ResultWrapper<DocumentIdListResponse?> {
        return when(val call = safeApiCall { apiService.getDocumentAndIdsList() }){
            is ResultWrapper.Success ->{
                ResultWrapper.Success(call.value)
            }
            is ResultWrapper.GenericError -> ResultWrapper.GenericError()
            ResultWrapper.SocketTimeOutError -> ResultWrapper.SocketTimeOutError
            ResultWrapper.NetworkError -> ResultWrapper.NetworkError
        }
    }

    suspend fun signContractToken(body:HashMap<String,String?>): ResultWrapper<ResponseBody?> {
        return when(val call = safeApiCall { apiService.signContractToken(body) }){
            is ResultWrapper.Success ->{
                ResultWrapper.Success(call.value)
            }
            is ResultWrapper.GenericError -> ResultWrapper.GenericError()
            ResultWrapper.SocketTimeOutError -> ResultWrapper.SocketTimeOutError
            ResultWrapper.NetworkError -> ResultWrapper.NetworkError
        }
    }

    suspend fun uploadClaim(body:HashMap<String,String?>): ResultWrapper<ResponseBody?> {
        return when(val call = safeApiCall { apiService.uploadClaim(body) }){
            is ResultWrapper.Success ->{
                ResultWrapper.Success(call.value)
            }
            is ResultWrapper.GenericError -> ResultWrapper.GenericError()
            ResultWrapper.SocketTimeOutError -> ResultWrapper.SocketTimeOutError
            ResultWrapper.NetworkError -> ResultWrapper.NetworkError
        }
    }

    suspend fun policyUpdate(body:HashMap<String,String?>): ResultWrapper<ResponseBody?> {
        return when(val call = safeApiCall { apiService.policyUpdate(body) }){
            is ResultWrapper.Success ->{
                ResultWrapper.Success(call.value)
            }
            is ResultWrapper.GenericError -> ResultWrapper.GenericError()
            ResultWrapper.SocketTimeOutError -> ResultWrapper.SocketTimeOutError
            ResultWrapper.NetworkError -> ResultWrapper.NetworkError
        }
    }


    suspend fun getProfileInfo(): ResultWrapper<ProfileDetailResponse?> {
        return when(val call = safeApiCall { apiService.getProfileInfo() }){
            is ResultWrapper.Success ->{
                ResultWrapper.Success(call.value)
            }
            is ResultWrapper.GenericError -> ResultWrapper.GenericError()
            ResultWrapper.SocketTimeOutError -> ResultWrapper.SocketTimeOutError
            ResultWrapper.NetworkError -> ResultWrapper.NetworkError
        }
    }
}
