package com.example.ks.di

import com.example.ks.api.Constants
import com.example.ks.models.RefreshTokenResponse
import com.example.ks.repo.AuthRepo
import com.example.ks.sharedpref.SharedPreferenceHelper
import com.example.ks.utils.AppConstant
import com.example.ks.utils.ResultWrapper
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*

/**
 * Created by skycap.
 */
class AuthInterceptor(private val  sharedPreferenceHelper: SharedPreferenceHelper,
                      ) : Interceptor {
    override  fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        // DONT INCLUDE API KEYS IN YOUR SOURCE CODE
        sharedPreferenceHelper.getAccessToken()?.let {
            req=req.newBuilder().apply {
                addHeader("Authorization","Bearer $it")
                addHeader( "Accept", "application/json")
            }.build()
        }

        var response= chain.proceed(req)
        if(response.code==401){
            val refreshTokenBody = FormBody.Builder()
                .add("refresh_token", sharedPreferenceHelper.getUser()?.data?.token?.refreshToken?:"")

                .build()
            val refreshTokenRequest = Request.Builder()
                .addHeader("Accept", "application/json")
                .url(Constants.BASE_URL_DEV + "refresh-token")
                .post(refreshTokenBody)
                .build()
            val refreshTokenResponse = chain.proceed(refreshTokenRequest)
            if (refreshTokenResponse.code == 200) {
                val fromJson = Gson().fromJson<RefreshTokenResponse>(
                    Gson().toJson(refreshTokenResponse.body),
                    RefreshTokenResponse::class.java
                )
                sharedPreferenceHelper.saveRefreshToken(fromJson.data?.accessToken)
                sharedPreferenceHelper.saveRefreshToken(fromJson.data?.refreshToken)
                response=chain.proceed(buildRequest(chain,fromJson.data?.accessToken))
            }
        }
        return response
    }
    private fun buildRequest(chain: Interceptor.Chain, token: String?,): Request {

        val original = chain.request()


            original.newBuilder()
                .method(original.method, original.body)
                .addHeader("Accept", "application/json")
                .header("Authorization", "Bearer $token")
//                .addHeader("device-token", deviceId)
                .build()

        return original
    }

}