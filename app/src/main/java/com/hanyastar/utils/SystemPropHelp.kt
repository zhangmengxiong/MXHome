package com.hanyastar.utils

import android.os.SystemProperties

/**
 * 获取系统属性
 * Created by zmx on 2015/8/5.
 */
object SystemPropHelp {
    fun get(key: String, default: String = ""): String {
        var value = SystemProperties.get(key)?.trim()
        if (value?.isBlank() == true) {
            value = default
        }
        return value ?: default
    }

    fun set(key: String, value: String): Boolean {
        try {
            SystemProperties.set(key, value)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }
}
