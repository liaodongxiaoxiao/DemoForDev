package com.karl.demo.android

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.karl.demo.BaseActivity
import com.karl.demo.R
import com.karl.demo.databinding.ActivityAndroidMotion2LayoutBinding
import com.karl.demo.extesion.justStartActivity
import com.karl.kotlin.extension.inflate

class MotionLayout2Activity :
    BaseActivity<ActivityAndroidMotion2LayoutBinding>(ActivityAndroidMotion2LayoutBinding::inflate) {

    private val TAG = "MotionLayout2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.root.setDebugMode(MotionLayout.DEBUG_SHOW_PATH)

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
            rv.layoutManager = LinearLayoutManager(this@MotionLayout2Activity)
            rv.adapter = stringAdapter

            tvTitle.setOnClickListener {
                justStartActivity(MotionLayout4Activity::class.java)
            }

            root.addTransitionListener(object : MotionLayout.TransitionListener {
                override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
                    Log.e(TAG, "onTransitionStarted: ")
                }

                override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
                    Log.e(TAG, "onTransitionChange: ")
                }

                override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                    Log.e(TAG, "onTransitionCompleted: ")
                }

                override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
                    Log.e(TAG, "onTransitionTrigger: ")
                }
            })
        }

    }
}

class StringAdapter(private val data: MutableList<String>) :
    RecyclerView.Adapter<StringAdapter.StringViewHolder>() {

    inner class StringViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv: TextView = view.findViewById(R.id.tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder {
        return StringViewHolder(parent.inflate(R.layout.item_string))
    }

    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
        holder.tv.text = data[position]
    }

    override fun getItemCount(): Int = data.size
}