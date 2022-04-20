package com.karl.demo.android

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.LinearLayoutManager
import com.karl.demo.BaseActivity
import com.karl.demo.R
import com.karl.demo.databinding.ActivityMotionLayoutScrollBinding
import kotlin.math.min

class MotionLayout333Activity :
    BaseActivity<ActivityMotionLayoutScrollBinding>(ActivityMotionLayoutScrollBinding::inflate) {
    private var scrollY = 0
    private var scrollPosition: Double = 0.0
    private var direction: Direction = Direction.NONE
    private var upAnimation1 = false
    private var upAnimation2 = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_motion_layout4)
        //setContentView(R.layout.activity_motion_layout_scroll)
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
                changTitleView()

                scrollPosition = ((height + sy) * 100.0 / view.bottom)

                /*if (scrollPosition > 70 && direction == Direction.UP) {
                    if (!upAnimation1) {
                        ml_4.setTransition(R.id.start, R.id.middle)
                        ml_4.transitionToEnd()
                    } else if (!upAnimation2) {
                        if (scrollPosition > 74) {
                            ml_4.setTransition(R.id.middle, R.id.end)
                            ml_4.transitionToEnd()
                        }
                    }
                }*/
            })
        }


        /*ml_4.addTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
                "onTransitionStarted start id:$p1 end id:$p2".log()

            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
                //"onTransitionChange start id:$p1 end id:$p2 progress:$p3".log()
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                "onTransitionCompleted currentId id:$p1 ".log()
                if (p1 == R.id.middle) {
                    upAnimation1 = true
                    "up1: $scrollPosition".log()
                } else if (p1 == R.id.end) {
                    upAnimation2 = true
                    "up2: $scrollPosition".log()
                }
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {

            }

        })*/

    }

    private fun changTitleView() {
        val height = resources.getDimension(R.dimen.dp_150)
        //获取渐变率
        val scale = scrollY.toFloat() / height
        setScale(scale)
    }


    /**
     * 白色图标alpha 1~0
     * 黑色图标alpha 0~1
     */
    private fun setScale(scale: Float) {
        //获取渐变数值
        var alpha = 1.0f * scale
        alpha = min(alpha, 1.toFloat())
        binding.tvTitle.apply {
            alpha = alpha
            //v_title_bg.background?.alpha = (alpha*100).toInt()
            background?.alpha = (alpha * 100).toInt()
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
            rv.layoutManager = LinearLayoutManager(this@MotionLayout333Activity)
            rv.adapter = stringAdapter
        }
    }
}
