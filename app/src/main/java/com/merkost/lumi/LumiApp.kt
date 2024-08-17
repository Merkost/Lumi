package com.merkost.lumi

import android.app.Application
import com.merkost.lumi.di.networkModule
import com.merkost.lumi.di.repositoryModule
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class LumiApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            modules(networkModule, repositoryModule)
        }
    }
}