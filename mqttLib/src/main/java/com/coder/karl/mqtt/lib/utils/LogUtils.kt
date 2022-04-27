package com.coder.karl.mqtt.lib.utils

import android.util.Log

object LogUtils {
    private const val tag = "mqtt-lib"
    private var showLog = false

    fun setShowLog(isShow: Boolean) {
        this.showLog = isShow
    }

    fun e(message: String) {
        if (showLog) {
            Log.e(tag, message)
        }
    }

    fun e(message: String, error: Throwable?) {
        if (showLog) {
            Log.e(tag, message, error)
        }
    }


    fun d(message: String) {
        if (showLog) {
            Log.d(tag, message)
        }
    }

    fun d(message: String, error: Throwable?) {
        if (showLog) {
            Log.d(tag, message, error)
        }
    }
}