package com.karl.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karl.demo.extesion.justStartActivity
import com.karl.demo.third.FlexboxLayoutActivity
import com.karl.demo.third.JsoupActivity
import com.karl.demo.third.MMKVActivity
import com.karl.demo.third.SmartRefreshLayoutActivity
import kotlinx.android.synthetic.main.activity_third_lib.*

class ThirdLibActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_lib)

        btn_flex_box_layout.setOnClickListener {
            //google flexbox layout
            startActivity(Intent(this, FlexboxLayoutActivity::class.java))
        }
        btn_jsoup.setOnClickListener {
            //jsoup
            startActivity(Intent(this, JsoupActivity::class.java))
        }

        btn_mmkv.setOnClickListener {
            justStartActivity(MMKVActivity::class.java)
        }

        btn_refresh.setOnClickListener {
            justStartActivity(SmartRefreshLayoutActivity::class.java)
        }

    }
}