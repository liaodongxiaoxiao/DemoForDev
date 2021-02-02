package com.karl.demo.extesion

import android.app.Activity
import android.content.Context
import android.content.Intent

inline fun <T : Activity> Context.justStartActivity(clazz: Class<T>) {
    this.startActivity(Intent(this, clazz))
}