package com.karl.demo

import android.content.Intent
import android.os.Bundle
import com.karl.demo.databinding.ActivityMainBinding
import com.karl.demo.extesion.justStartActivity

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btnAndroid.setOnClickListener {
            startActivity(Intent(this, AndroidActivity::class.java))
        }

        binding.btnThird.setOnClickListener {
            startActivity(Intent(this, ThirdLibActivity::class.java))
        }

        binding.btnDemo.setOnClickListener {
            justStartActivity(DemoActivity::class.java)
        }

        binding.btnTools.setOnClickListener {
            justStartActivity(ToolsPageActivity::class.java)
        }

    }
}