package com.antartic.sudio.catest.core

import android.app.Application
import com.antartic.sudio.catest.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class CATestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
