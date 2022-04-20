package com.karl.demo.android

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import com.karl.demo.BaseActivity
import com.karl.demo.R
import com.karl.demo.databinding.ActivityAndroidMotionLayoutBinding
import com.karl.demo.extesion.justStartActivity
import com.karl.kotlin.extension.log
import com.karl.kotlin.extension.toast

class MotionLayoutActivity :
    BaseActivity<ActivityAndroidMotionLayoutBinding>(ActivityAndroidMotionLayoutBinding::inflate) {

    private var isClosing = true
    private var progressTemp = -1.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_android_motion_layout)
        binding.apply {
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
                            etSearch.visibility = View.VISIBLE
                            ivSearch.visibility = View.GONE
                            ivShoppingCard.setImageResource(R.drawable.icon_shopping_cart_dark)
                        } else {
                            etSearch.visibility = View.GONE
                            ivSearch.visibility = View.VISIBLE
                            ivShoppingCard.setImageResource(R.drawable.icon_shopping_cart)
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

            ivSearch.setOnClickListener {
                justStartActivity(MotionLayout3Activity::class.java)
            }

            ivShoppingCard.setOnClickListener {
                justStartActivity(MotionLayout2Activity::class.java)
            }

            etSearch.setOnClickListener {
                toast("et_search")
            }
        }

    }
}