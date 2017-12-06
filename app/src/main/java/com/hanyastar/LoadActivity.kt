package com.hanyastar

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import com.hanyastar.service.MyService
import com.hanyastar.utils.SystemPropHelp

class LoadActivity : BaseActivity() {
    private val launcherPkg: String = SystemPropHelp.get("persist.sys.mx_launcher", "com.dangbei.tvlauncher")
    private val settingPkg = SystemPropHelp.get("persist.sys.mx_setting", "com.hanyastar.systemsetting")

    private val mHandler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        startService(Intent(this, MyService::class.java))
    }

    private var ticketRun: Runnable? = null

    init {
        ticketRun = Runnable {
            try {
                if (isShowing()) {
                    startMain()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                synchronized(SYNC_OBJ) {
                    mHandler.removeCallbacksAndMessages(null)
                    mHandler.postDelayed(ticketRun, 5000)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        startMain()
        synchronized(SYNC_OBJ) {
            mHandler.removeCallbacksAndMessages(null)
            mHandler.postDelayed(ticketRun, 5000)
        }
    }

    override fun onStop() {
        synchronized(SYNC_OBJ) {
            mHandler.removeCallbacksAndMessages(null)
        }
        super.onStop()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_MENU) {
            settingPkg.let { startApp(it) }
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun startMain() {
        synchronized(SYNC_OBJ) {
            launcherPkg.let { startApp(it) }
        }
    }

    private fun startApp(pkg: String) {
        Log.v(TAG, "启动：" + pkg)
        try {
            val intent = packageManager.getLaunchIntentForPackage(pkg)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        mHandler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}
