package com.eress.closer.util

import android.app.Application
import android.content.res.Configuration

class ApplicationUtil : Application() {

    var intro = 0
    var flag = 0

    override fun onCreate() {
        super.onCreate()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}
