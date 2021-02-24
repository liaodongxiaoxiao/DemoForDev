package com.karl.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karl.demo.extesion.justStartActivity
import com.karl.demo.tools.OpacityActivity
import kotlinx.android.synthetic.main.activity_tools_page.*

/**
 * 工具类
 */
class ToolsPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tools_page)

        btn_tools_background.setOnClickListener {
            justStartActivity(OpacityActivity::class.java)
        }
    }
}