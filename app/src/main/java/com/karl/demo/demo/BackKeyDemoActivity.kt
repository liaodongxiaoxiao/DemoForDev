package com.karl.demo.demo

import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.karl.demo.R
import com.karl.kotlin.extension.log

class BackKeyDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_back_key)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            "点击返回键(keycode:${keyCode})".log()

            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.apply {
                setTitle("你是否要放弃支付？")
                setPositiveButton("放弃") { _, _ -> finish() }

                setNegativeButton("再想想") { _, _ -> "不退出".log() }
            }
            alertDialogBuilder.create().show()

            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        "on back pressed".log()
    }
}