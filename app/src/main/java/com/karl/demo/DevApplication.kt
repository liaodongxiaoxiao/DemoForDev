package com.karl.demo

import android.app.Application
import com.tencent.mmkv.MMKV
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DevApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
    }
}