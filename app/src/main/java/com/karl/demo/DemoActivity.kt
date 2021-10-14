package com.karl.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.karl.demo.demo.*
import com.karl.demo.extesion.justStartActivity
import kotlinx.android.synthetic.main.activity_demo.*

class DemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        btn_demo_scrollview.setOnClickListener {
            justStartActivity(ScrollViewDemoActivity::class.java)
        }

        btn_demo_back.setOnClickListener {
            justStartActivity(BackKeyDemoActivity::class.java)
        }

        btn_demo_mqtt.setOnClickListener {
            justStartActivity(MQTTDemoActivity::class.java)
        }

        btn_demo_html.setOnClickListener {
            justStartActivity(HtmlDemoActivity::class.java)
        }

        btn_okhttp_util.setOnClickListener {
            justStartActivity(OkHttpUtilsDemoActivity::class.java)
        }

        btn_Scrollview_demo.setOnClickListener {
            justStartActivity(ScrollViewDemoActivity::class.java)
        }

        btn_Scrollview_bottom.setOnClickListener {
            justStartActivity(ScrollViewListenerDemoActivity::class.java)
        }

        btn_view_to_image.setOnClickListener {
            justStartActivity(ViewToImageActivity::class.java)
        }

        btn_to_do.setOnClickListener {
            justStartActivity(LoginActivity::class.java)
        }

        btn_weather.setOnClickListener {
            justStartActivity(WeatherDemoActivity::class.java)
        }

        btn_lottery.setOnClickListener {
            justStartActivity(LotteryDemoActivity::class.java)
        }

    }
}