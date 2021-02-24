package com.karl.demo.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.karl.demo.R
import com.karl.kotlin.extension.inflate
import com.karl.kotlin.extension.log
import com.karl.kotlin.extension.toast
import kotlinx.android.synthetic.main.activity_android_motion2_layout.*
import kotlinx.android.synthetic.main.activity_android_motion3_layout.*
import kotlinx.android.synthetic.main.activity_android_motion_layout.*
import kotlinx.android.synthetic.main.activity_android_motion_layout.root

class MotionLayout3Activity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_android_motion3_layout)
        //motion_layout.setDebugMode(MotionLayout.DEBUG_SHOW_PATH)



    }
}

