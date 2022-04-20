package com.karl.demo.android

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import com.karl.demo.BaseActivity
import com.karl.demo.databinding.ActivityAndroidMotion3LayoutBinding

class MotionLayout3Activity :
    BaseActivity<ActivityAndroidMotion3LayoutBinding>(ActivityAndroidMotion3LayoutBinding::inflate) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.motionLayout.setDebugMode(MotionLayout.DEBUG_SHOW_PATH)
        binding.ivBg.isEnabled = false
        binding.ivBg.setOnTouchListener(object : View.OnTouchListener {

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                Log.e("TAG", event?.action.toString())
                return true
            }

        })


    }
}

