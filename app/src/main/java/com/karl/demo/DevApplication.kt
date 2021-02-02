package com.karl.demo

import android.app.Application
import com.tencent.mmkv.MMKV

class DevApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
    }
}