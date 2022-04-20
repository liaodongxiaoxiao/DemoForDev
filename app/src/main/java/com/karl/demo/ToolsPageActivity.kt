package com.karl.demo

import android.os.Bundle
import com.karl.demo.databinding.ActivityToolsPageBinding
import com.karl.demo.extesion.justStartActivity
import com.karl.demo.tools.OpacityActivity

/**
 * 工具类
 */
class ToolsPageActivity : BaseActivity<ActivityToolsPageBinding>(ActivityToolsPageBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_tools_page)

        binding.btnToolsBackground.setOnClickListener {
            justStartActivity(OpacityActivity::class.java)
        }
    }
}