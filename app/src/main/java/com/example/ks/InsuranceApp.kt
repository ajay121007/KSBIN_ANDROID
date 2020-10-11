package com.example.ks

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.downloader.PRDownloader
import com.downloader.PRDownloaderConfig
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
        startKoin(this, listOf(viewModels, networkModule, repos))


// Setting timeout globally for the download network requests:

// Setting timeout globally for the download network requests:
        val config = PRDownloaderConfig.newBuilder()
            .setReadTimeout(30000)
            .setDatabaseEnabled(true)
            .setConnectTimeout(30000)
            .build()
        PRDownloader.initialize(applicationContext, config)
    }
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}