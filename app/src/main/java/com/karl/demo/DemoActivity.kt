package com.karl.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.karl.demo.demo.ScrollViewDemoActivity
import com.karl.demo.extesion.justStartActivity
import kotlinx.android.synthetic.main.activity_demo.*

class DemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        btn_demo_scrollview.setOnClickListener {
            justStartActivity(ScrollViewDemoActivity::class.java)
        }

    }
}