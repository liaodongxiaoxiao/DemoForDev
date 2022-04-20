package com.karl.demo.android

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.karl.demo.BaseActivity
import com.karl.demo.R
import com.karl.demo.databinding.ActivityMotionLayout4Binding
import com.karl.kotlin.extension.log

class MotionLayout4Activity :
    BaseActivity<ActivityMotionLayout4Binding>(ActivityMotionLayout4Binding::inflate) {
    private var scrollY = 0
    private var scrollPosition: Double = 0.0
    private var direction: Direction = Direction.NONE
    private var upAnimation1 = false
    private var upAnimation2 = false
    private var downAnimation1 = false
    private var downAnimation2 = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_motion_layout4)
        initRL()

        binding.apply {
            scroll.viewTreeObserver.addOnScrollChangedListener(ViewTreeObserver.OnScrollChangedListener {
                val view: View = scroll.getChildAt(scroll.childCount - 1) as View
                val height = scroll.height
                val sy = scroll.scrollY
                if (sy > scrollY) {
                    direction = Direction.UP
                } else if (sy < scrollY) {
                    direction = Direction.DOWN
                }

                scrollY = sy

                if (scrollY == 0) {
                    upAnimation1 = false
                    upAnimation2 = false
                    downAnimation1 = false
                    downAnimation2 = false
                }

                //changTitleView()

                scrollPosition = ((height + sy) * 100.0 / view.bottom)

                "position:$scrollPosition".log()

                if (scrollPosition > 70 && direction == Direction.UP) {

                    if (!upAnimation1) {
                        ml4.setTransition(R.id.start, R.id.middle)
                        ml4.transitionToEnd()
                        upAnimation1 = true
                    } else if (!upAnimation2) {
                        if (scrollPosition > 74) {
                            ml4.setTransition(R.id.middle, R.id.end)
                            ml4.transitionToEnd()
                            upAnimation2 = true
                        }
                    }

                }

                if (scrollPosition < 74 && direction == Direction.DOWN) {
                    if (!downAnimation1) {
                        //"scrollPosition:$scrollPosition direction:"
                        ml4.setTransition(R.id.end, R.id.middle)
                        ml4.transitionToEnd()
                        downAnimation1 = true
                    }

                    if (!downAnimation2 && scrollPosition < 70) {
                        ml4.setTransition(R.id.middle, R.id.start)
                        ml4.transitionToEnd()
                        downAnimation2 = true
                    }
                }

            })

            ml4.addTransitionListener(object : MotionLayout.TransitionListener {
                override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
                    //"onTransitionStarted start id:$p1 end id:$p2".log()

                }

                override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
                    //"onTransitionChange start id:$p1 end id:$p2 progress:$p3".log()
                }

                override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                    "onTransitionCompleted currentId id:${getCurrentIdName(p1)} ".log()
                    if (p1 == R.id.middle) {
                        if (direction == Direction.UP) {
                            upAnimation1 = true
                            "up1: $scrollPosition".log()
                        } else {
                            downAnimation1 = true
                            "down1: $scrollPosition".log()
                        }

                    } else if (p1 == R.id.end) {
                        upAnimation2 = true
                        "up2: $scrollPosition".log()
                    } else if (p1 == R.id.start) {
                        downAnimation2 = true
                        "down2: $scrollPosition".log()
                    }
                }

                override fun onTransitionTrigger(
                    p0: MotionLayout?,
                    p1: Int,
                    p2: Boolean,
                    p3: Float
                ) {

                }

            })
        }

    }

    private fun getCurrentIdName(id: Int): String {
        return when (id) {
            R.id.start -> "start"
            R.id.middle -> "middle"
            R.id.end -> "end"
            else -> "unknown"
        }
    }


    private fun initRL() {
        val data: MutableList<String> = mutableListOf()
        data.add("切尔西")
        data.add("曼联")
        data.add("曼城")
        data.add("热刺")
        data.add("AC米兰")
        data.add("国际米兰")
        data.add("罗马")
        data.add("尤文图斯")
        data.add("皇马")
        data.add("巴塞罗那")
        data.add("马德里竞技")
        data.add("西班牙人")
        data.add("拜仁慕尼黑")
        data.add("多特蒙德")
        data.add("沙尔克04")
        data.add("巴黎圣日耳曼")
        data.add("里昂")
        data.add("广州恒大淘宝")
        data.add("江苏苏宁易购")
        data.add("山东鲁能泰山")
        data.add("上海绿地申花")

        val stringAdapter = StringAdapter(data)
        binding.apply {
            rv.layoutManager = LinearLayoutManager(this@MotionLayout4Activity)
            rv.adapter = stringAdapter
        }
    }
}

enum class Direction {
    NONE,
    UP,
    DOWN
}