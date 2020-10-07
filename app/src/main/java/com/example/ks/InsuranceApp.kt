package com.example.ks

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.example.ks.di.networkModule
import com.example.ks.di.repos
import com.example.ks.di.viewModels
import org.koin.android.ext.android.startKoin


/**
 * Created by skycap.
 */
class InsuranceApp:Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(viewModels,networkModule,repos))}
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}