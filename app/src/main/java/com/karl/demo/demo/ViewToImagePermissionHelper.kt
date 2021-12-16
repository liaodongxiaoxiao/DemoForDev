package com.karl.demo.demo

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat


class ViewToImagePermissionHelper {
    private val needPermission = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_CONTACTS
    )

    private val requestPermissionCode = 2022

    private var activity: Activity

    private var method: () -> Unit = {}


    private constructor(activity: Activity) {
        this.activity = activity
    }

    companion object {
        @Volatile
        private var helper: ViewToImagePermissionHelper? = null

        fun getInstance(activity: Activity): ViewToImagePermissionHelper {
            helper ?: synchronized(this) {
                helper = ViewToImagePermissionHelper(activity)
            }

            return helper!!
        }
    }

    fun execute(method: () -> Unit) {
        Log.e("TAG", "execute: " )
        this.method = method
        toCallMethod()
    }

    fun reCallMethod(requestCode: Int) {
        Log.e("TAG", "re call")
        if (requestCode == requestPermissionCode) {
            toCallMethod()
        }
    }

    private fun toCallMethod() {
        Log.e("TAG","call1")
        if (checkAllPermissionsGranted()) {
            Log.e("TAG","call2")
            method.invoke()
        } else {
            Log.e("TAG","call3")
            requestPermission()
        }
    }


    private fun checkAllPermissionsGranted(): Boolean {
        var result: Int
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        for (p in needPermission) {
            result = ActivityCompat.checkSelfPermission(activity, p)
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p)
                break
            }
        }
        return listPermissionsNeeded.isEmpty()
    }


    private fun requestPermission() {

        ActivityCompat.requestPermissions(activity, needPermission, requestPermissionCode)
    }
}