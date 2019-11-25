package com.buck

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.antonioleiva.weatherapp.extensions.DelegatesExt

class App : MultiDexApplication() {
    companion object {
        var instance: App by DelegatesExt.notNullSingleValue()

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}