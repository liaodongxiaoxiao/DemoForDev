package com.karl.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karl.demo.extesion.justStartActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_android.setOnClickListener {
            startActivity(Intent(this, AndroidActivity::class.java))
        }

        btn_third.setOnClickListener {
            startActivity(Intent(this, ThirdLibActivity::class.java))
        }

        btn_demo.setOnClickListener {
            justStartActivity(DemoActivity::class.java)
        }

    }
}