package com.example.ks.repo

import com.example.ks.activities.payment.PaymentResponse
import com.example.ks.api.ApiService
import com.example.ks.model.contarctListResponse.ContractResponse
import com.example.ks.model.contarctListResponse.SignTokenResponse
import com.example.ks.model.documentid.DocumentIdListResponse
import com.example.ks.model.invoice.InvoiceListResponse
import com.example.ks.model.profile.ProfileDetailResponse
import com.example.ks.model.upload.UploadResponse
import com.example.ks.model.uploadClaim.UploadClaimImageResponse
import com.example.ks.models.LoginResponse
import com.example.ks.models.ProfileResponse
import com.example.ks.models.SignUpResponse
import com.example.ks.sharedpref.SharedPreferenceHelper
import com.example.ks.utils.ResultWrapper
import com.example.ks.utils.safeApiCall
import okhttp3.MultipartBody
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


    suspend fun getContractList(): ResultWrapper<ContractResponse?> {
        return when(val call = safeApiCall { apiService.getContractList() }){
            is ResultWrapper.Success ->{
                ResultWrapper.Success(call.value)
            }
            is ResultWrapper.GenericError -> ResultWrapper.GenericError()
            ResultWrapper.SocketTimeOutError -> ResultWrapper.SocketTimeOutError
            ResultWrapper.NetworkError -> ResultWrapper.NetworkError
        }
    }

    suspend fun getInvoice(): ResultWrapper<PaymentResponse?> {
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

    suspend fun signContractToken(body:HashMap<String,String?>): ResultWrapper<SignTokenResponse?> {
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


    suspend fun getProfileInfo(): ResultWrapper<ProfileResponse?> {
        return when(val call = safeApiCall { apiService.getProfileInfo() }){
            is ResultWrapper.Success ->{
                ResultWrapper.Success(call.value)
            }
            is ResultWrapper.GenericError -> ResultWrapper.GenericError()
            ResultWrapper.SocketTimeOutError -> ResultWrapper.SocketTimeOutError
            ResultWrapper.NetworkError -> ResultWrapper.NetworkError
        }
    }

    suspend fun uploadDocs(fileDocs: MultipartBody.Part,
                           fileName: MultipartBody.Part,): ResultWrapper<UploadResponse?> {
        return when(val call = safeApiCall { apiService.uploadDocs(fileDocs,fileName) }){
            is ResultWrapper.Success ->{
                ResultWrapper.Success(call.value)
            }
            is ResultWrapper.GenericError -> ResultWrapper.GenericError()
            ResultWrapper.SocketTimeOutError -> ResultWrapper.SocketTimeOutError
            ResultWrapper.NetworkError -> ResultWrapper.NetworkError
        }
    }

    suspend fun uploadClaims(fileDocs: MultipartBody.Part,
                           fileName: MultipartBody.Part,): ResultWrapper<UploadClaimImageResponse?> {
        return when(val call = safeApiCall { apiService.uploadClaimDocs(fileDocs,fileName) }){
            is ResultWrapper.Success ->{
                ResultWrapper.Success(call.value)
            }
            is ResultWrapper.GenericError -> ResultWrapper.GenericError()
            ResultWrapper.SocketTimeOutError -> ResultWrapper.SocketTimeOutError
            ResultWrapper.NetworkError -> ResultWrapper.NetworkError
        }
    }
}
