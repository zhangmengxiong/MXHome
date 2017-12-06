package com.hanyastar

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import java.util.concurrent.atomic.AtomicBoolean


/**
 * Activity基类
 * 创建人： zhangmengxiong
 * 创建时间： 2017/4/25.
 * 联系方式: zmx_final@163.com
 */
open class BaseActivity : Activity() {
    protected val TAG: String = javaClass.simpleName
    private val isRunning = AtomicBoolean(false)
    private val isShowing = AtomicBoolean(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        super.onCreate(savedInstanceState)
        isRunning.set(true)
    }

    public override fun onResume() {
        super.onResume()
        isShowing.set(true)
    }

    public override fun onPause() {
        isShowing.set(false)
        super.onPause()
    }

    /**
     * 判断Activity是否存活

     * @return
     */
    fun isRunning(): Boolean {
        return isRunning.get()
    }

    /**
     * 判断界面是否正在显示
     */
    fun isShowing(): Boolean = isShowing.get()

    /**
     * Toast消息提示
     */
    protected fun showToast(msg: String) {
        if (!isRunning()) return
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * Toast消息提示
     */
    fun showToast(msgRes: Int) {
        if (!isRunning()) return
        Toast.makeText(this, msgRes, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        Log.v(TAG, "onDestroy()")
        isRunning.set(false)
        super.onDestroy()
    }

    companion object {
        val SYNC_OBJ = Any()
    }
}
