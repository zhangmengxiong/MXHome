package com.hanyastar.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.util.Log
import com.hanyastar.utils.MyApp
import com.hanyastar.utils.SystemPropHelp

class MyService : Service() {
    private val TAG: String = MyService::class.java.simpleName
    private val startupOpenPkg = SystemPropHelp.get("persist.sys.mx_open", "com.dianshijia.newlive")

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        if (MyApp.isFirstLaunch) {
            Handler().postDelayed({
                startupOpenPkg.let {
                    startApp(it)
                }
            }, 10000)
            MyApp.isFirstLaunch = false
        }
    }

    private fun startApp(pkg: String) {
        Log.v(TAG, "启动：" + pkg)
        try {
            val intent = packageManager.getLaunchIntentForPackage(pkg)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
