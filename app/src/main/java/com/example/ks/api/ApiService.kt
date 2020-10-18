package com.example.ks.api



import com.example.ks.model.documentid.DocumentIdListResponse
import com.example.ks.model.invoice.InvoiceListResponse
import com.example.ks.model.profile.ProfileDetailResponse

import com.example.ks.activities.payment.PaymentResponse

import com.example.ks.model.upload.UploadResponse

import com.example.ks.model.contarctListResponse.ContractResponse
import com.example.ks.model.contarctListResponse.SignTokenResponse
import com.example.ks.model.forgot.ForgotPasswordResponse
import com.example.ks.model.policy.PolicyUpdateResponse
import com.example.ks.model.renewals.RenewalResponse
import com.example.ks.model.uploadClaim.UploadClaimImageResponse
import com.example.ks.models.*


import com.example.ks.utils.ErrorResponse
import com.example.ks.utils.ResultWrapper
import com.squareup.moshi.Moshi
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.http.*
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * Created by skycap.
 */
interface ApiService {

    @POST("signup")
    @FormUrlEncoded
    suspend fun signUp(
        @FieldMap map: HashMap<String,String?>
    ):Response<LoginResponse>

    @POST("login")
    @FormUrlEncoded
    suspend fun login(@FieldMap map: HashMap<String,String?>):Response<LoginResponse>

    @GET("dashboard")
    suspend fun getDashboardData():Response<DashBoardResponse>


    @GET("contracts")
    suspend fun getContractList():Response<ContractResponse>

    @GET("invoices")
    suspend fun getInvoiceList():Response<PaymentResponse?>

    @GET("documents")
    suspend fun getDocumentAndIdsList():Response<DocumentIdListResponse>

    @GET("logout")
    suspend fun logOut():Response<Any>

    @POST("sign-contract-token")
    @FormUrlEncoded
    suspend fun signContractToken(@FieldMap map: HashMap<String,String?>):Response<SignTokenResponse>

    @POST("upload-claim")
    suspend fun uploadClaim(@FieldMap map: HashMap<String,String?>):Response<ResponseBody>

    @FormUrlEncoded
    @POST("policy-update")
    suspend fun policyUpdate(@FieldMap map: HashMap<String,String?>):Response<PolicyUpdateResponse>


    @FormUrlEncoded
    @POST("forget-password")
    suspend fun forgotPassword(@FieldMap map: HashMap<String,String?>):Response<ForgotPasswordResponse>

    @GET("profile-details")
    suspend fun getProfileInfo():Response<ProfileResponse>

    @Multipart
    @POST("upload-document")
    suspend fun uploadDocs(@Part fileDocs: MultipartBody.Part, @Part fileName: MultipartBody.Part): Response<UploadResponse>


    @Multipart
    @POST("upload-claim")
    suspend fun uploadClaimDocs(@Part fileDocs: MultipartBody.Part, @Part fileName: MultipartBody.Part): Response<UploadClaimImageResponse>


    @Multipart
    @POST("update-profile")
    suspend fun updateProfile(@Part userImage: MultipartBody.Part, @Part userName: MultipartBody.Part,
                              @Part userPhone: MultipartBody.Part,
                              @Part userEmail: MultipartBody.Part): Response<UploadClaimImageResponse>


    @POST("policy-update")
    @FormUrlEncoded
    suspend fun updatePolicy(@FieldMap updatePolicyPArms: HashMap<String,String?>):Response<PolicyUpdateResponse>

    @FormUrlEncoded
    @POST("invoice-payment")
    suspend fun createPaymentToken(@Field("invoice_id") id:Int):Response<SignTokenResponse>

    @FormUrlEncoded
    @POST("refresh-token")
    suspend fun refreshToken(@Field("refresh_token") token:String?):Response<RefreshTokenResponse>


    @GET("renewals-list")
    suspend fun getRenewalsList():Response<RenewalResponse>

    @Multipart
    @POST("update-profile")
    suspend fun uploadRenewal(@Part dmv: MultipartBody.Part?,
                              @Part tcl: MultipartBody.Part?,
                              @Part ddc: MultipartBody.Part?,
    ): Response<UploadClaimImageResponse>

    @FormUrlEncoded
    @POST("document-renewal-payment")
    suspend fun renewalDocument(
        @Field("renewal_id",) id:Int,
        @Field("type",) type:String,
    ):Response<SignTokenResponse>

    @FormUrlEncoded
    @POST("card-payment")
    suspend fun cardPayment(
        @FieldMap map:HashMap<String,String>
    ):Response<PaymentResponse>

    @FormUrlEncoded
    @POST("save-bank-account")
    suspend fun bankPayment(
        @FieldMap map:HashMap<String,String>
    ):Response<PaymentResponse>
}



