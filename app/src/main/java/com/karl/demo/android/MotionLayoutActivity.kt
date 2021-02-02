package com.karl.demo.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import com.karl.demo.R
import com.karl.kotlin.extension.log
import com.karl.kotlin.extension.toast
import kotlinx.android.synthetic.main.activity_android_motion_layout.*

class MotionLayoutActivity : AppCompatActivity() {

    private var isClosing = true
    private var progressTemp = -1.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_android_motion_layout)
        root.setDebugMode(MotionLayout.DEBUG_SHOW_PATH)

        root.addTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, startId: Int, endId: Int) {
                //isClosing = endId == R.id.start
                "wz -> startId:${startId} endId:${endId}".log()
                "onTransitionStarted".log()
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, progress: Float) {
                "onTransitionChange progress:${progress}".log()
                if (progressTemp == -1f) {
                    progressTemp = progress
                    return
                }

                isClosing = progressTemp <= progress
                progressTemp = progress

                if (progress >= 0.5) {
                    if (isClosing) {
                        et_search.visibility = View.VISIBLE
                        iv_search.visibility = View.GONE
                        iv_shopping_card.setImageResource(R.drawable.icon_shopping_cart_dark)
                    } else {
                        et_search.visibility = View.GONE
                        iv_search.visibility = View.VISIBLE
                        iv_shopping_card.setImageResource(R.drawable.icon_shopping_cart)
                    }
                }
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                "onTransitionCompleted".log()
                progressTemp = -1.0f
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
                "onTransitionTrigger:${p3}".log()
            }
        })

        iv_search.setOnClickListener {
            toast("iv_search->")
        }

        iv_shopping_card.setOnClickListener {
            toast("iv_shopping_card")
        }

        et_search.setOnClickListener {
            toast("et_search")
        }
    }
}