package com.example.ks.di

import com.example.ks.sharedpref.SharedPreferenceHelper
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by skycap.
 */
class AuthInterceptor(private val  sharedPreferenceHelper: SharedPreferenceHelper) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        // DONT INCLUDE API KEYS IN YOUR SOURCE CODE
        sharedPreferenceHelper.getUser()?.let {
            req=req.newBuilder().addHeader("Authorization","Bearer ${it.data?.token?.accessToken}").build()
        }

        return chain.proceed(req)
    }
}