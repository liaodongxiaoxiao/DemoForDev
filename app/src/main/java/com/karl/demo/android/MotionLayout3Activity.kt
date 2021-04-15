package com.karl.demo.android

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import com.karl.demo.R
import kotlinx.android.synthetic.main.activity_android_motion3_layout.*

class MotionLayout3Activity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_android_motion3_layout)
        motion_layout.setDebugMode(MotionLayout.DEBUG_SHOW_PATH)
        iv_bg.isEnabled = false
        iv_bg.setOnTouchListener(object : View.OnTouchListener{

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                Log.e("TAG",event?.action.toString())
                return true
            }

        })


    }
}

