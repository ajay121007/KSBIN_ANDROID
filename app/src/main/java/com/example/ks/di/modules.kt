package com.example.ks.di



import android.content.Context
import com.example.ks.activities.claim.FileClaimViewModel

import com.example.ks.activities.payment.PaymentViewModel

import com.example.ks.activities.dashboard.DashBoardViewModel
import com.example.ks.activities.document.IdCardDocumentViewModel
import com.example.ks.activities.editprofile.EditProfileViewModel
import com.example.ks.activities.forgot.ForgotPasswordViewModel
import com.example.ks.activities.loginsignup.LoginViewModel
import com.example.ks.activities.loginsignup.SignUpViewModel
import com.example.ks.activities.policydetail.ChangePolicyDetailViewModel
import com.example.ks.activities.profile.ProfileViewModel
import com.example.ks.activities.renewal.RenewalViewModel

import com.example.ks.activities.signabledocument.SignableDocumentModel
import com.example.ks.activities.upload.UploadViewModel
import com.example.ks.api.ApiService
import com.example.ks.api.Constants
import com.example.ks.common.UICallBacks
import com.example.ks.repo.AuthRepo
import com.example.ks.repo.UserRepo
import com.example.ks.sharedpref.SharedPreferenceHelper
import net.authorize.acceptsdk.AcceptSDKApiClient

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel


import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by skycap.
 */

val viewModels = module {
    viewModel { (uiCallBacks: UICallBacks)->SignUpViewModel(uiCallBacks,get()) }
    viewModel { (uiCallBacks: UICallBacks)->LoginViewModel(uiCallBacks,get()) }
    viewModel { (uiCallBacks: UICallBacks)->DashBoardViewModel(uiCallBacks,get()) }
    viewModel { (uiCallBacks: UICallBacks)->SignableDocumentModel(uiCallBacks,get()) }
    viewModel { (uiCallBacks: UICallBacks)-> PaymentViewModel(uiCallBacks,get(),get()) }
    viewModel { (uiCallBacks: UICallBacks)->IdCardDocumentViewModel(uiCallBacks,get()) }
    viewModel { (uiCallBacks: UICallBacks)->UploadViewModel(uiCallBacks,get()) }
    viewModel { (uiCallBacks: UICallBacks)->FileClaimViewModel(uiCallBacks,get()) }
    viewModel { (uiCallBacks: UICallBacks)->ProfileViewModel(uiCallBacks,get()) }
    viewModel { (uiCallBacks: UICallBacks)->EditProfileViewModel(uiCallBacks,get()) }

    viewModel { (uiCallBacks: UICallBacks)->ChangePolicyDetailViewModel(uiCallBacks,get()) }

    viewModel { (uiCallBacks: UICallBacks)->ForgotPasswordViewModel(uiCallBacks,get()) }
    viewModel { (uiCallBacks: UICallBacks)->RenewalViewModel(uiCallBacks,get()) }


}


val networkModule = module {
    single { AuthInterceptor(get()) }
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
    single { provideForecastApi(get()) }
    single { provideApiClient(androidContext()) }
    single { SharedPreferenceHelper(androidContext()) }
}
val repos= module {
    single { AuthRepo(get(),get()) }
    single { UserRepo(get()) }
}
fun provideRetrofit(authInterceptor: AuthInterceptor): Retrofit {
    return Retrofit.Builder().baseUrl(Constants.BASE_URL_DEV)
        .addConverterFactory(GsonConverterFactory.create())
        .client(provideOkHttpClient(authInterceptor))
        .build()
}

fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
    return OkHttpClient().newBuilder()
        .connectTimeout(50, TimeUnit.SECONDS)
        .writeTimeout(50, TimeUnit.SECONDS)
        .readTimeout(50, TimeUnit.SECONDS)
        .addInterceptor(authInterceptor)
        .addInterceptor(httpInterceptor()).build()
}

fun provideForecastApi(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
private fun httpInterceptor() = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}
fun provideApiClient(context: Context): AcceptSDKApiClient = AcceptSDKApiClient.Builder(
    context,
    AcceptSDKApiClient.Environment.SANDBOX
)
    .connectionTimeout(5000) // optional connection time out in milliseconds
    .build()

