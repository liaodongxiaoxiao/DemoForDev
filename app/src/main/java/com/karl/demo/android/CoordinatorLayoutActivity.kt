package com.karl.demo.android

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.karl.demo.BaseActivity
import com.karl.demo.R
import com.karl.demo.databinding.ActivityAndroidCoordinatorLayoutBinding

class CoordinatorLayoutActivity :
    BaseActivity<ActivityAndroidCoordinatorLayoutBinding>(ActivityAndroidCoordinatorLayoutBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_android_coordinator_layout)
        initRL()

        binding.apply {
            tb.addTab(tb.newTab().setText("Tab 1"))
            tb.addTab(tb.newTab().setText("Tab 2"))
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
            rv.layoutManager = LinearLayoutManager(this@CoordinatorLayoutActivity)
            rv.adapter = stringAdapter
        }
    }
}