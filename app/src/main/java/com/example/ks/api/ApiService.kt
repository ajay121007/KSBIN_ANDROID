package com.example.ks.api

import com.example.ks.models.DashBoardResponse
import com.example.ks.models.LoginResponse
import com.example.ks.models.SignUpResponse
import com.example.ks.utils.ErrorResponse
import com.example.ks.utils.ResultWrapper
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
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
    ):Response<SignUpResponse>

    @POST("login")
    @FormUrlEncoded
    suspend fun login(
        @FieldMap map: HashMap<String,String?>
    ):Response<LoginResponse>

    @GET("dashboard")
    suspend fun getDashboardData():Response<DashBoardResponse>
}

