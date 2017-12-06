package com.hanyastar.utils

import android.app.Application
import android.content.Intent
import com.hanyastar.service.MyService

/**
 * Created by ZMX on 2017/12/4.
 */

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        isFirstLaunch = true
        startService(Intent(this, MyService::class.java))
    }

    companion object {
        var isFirstLaunch = true
    }
}
