package com.karl.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.karl.demo.databinding.ActivityDemoBinding
import com.karl.demo.demo.*
import com.karl.demo.extesion.justStartActivity

class DemoActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        binding = ActivityDemoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        /*btn_demo_scrollview.setOnClickListener {
            justStartActivity(ScrollViewDemoActivity::class.java)
        }*/
        binding.btnDemoBack.setOnClickListener {
            justStartActivity(BackKeyDemoActivity::class.java)
        }

        binding.btnDemoMqtt.setOnClickListener {
            justStartActivity(MQTTDemoActivity::class.java)
        }

        binding.btnDemoHtml.setOnClickListener {
            justStartActivity(HtmlDemoActivity::class.java)
        }

        binding.btnOkhttpUtil.setOnClickListener {
            justStartActivity(OkHttpUtilsDemoActivity::class.java)
        }

        binding.btnScrollviewDemo.setOnClickListener {
            justStartActivity(ScrollViewDemoActivity::class.java)
        }

        binding.btnScrollviewBottom.setOnClickListener {
            justStartActivity(ScrollViewListenerDemoActivity::class.java)
        }

        binding.btnViewToImage.setOnClickListener {
            justStartActivity(ViewToImageActivity::class.java)
        }

        binding.btnToDo.setOnClickListener {
            justStartActivity(LoginActivity::class.java)
        }

        binding.btnWeather.setOnClickListener {
            justStartActivity(WeatherDemoActivity::class.java)
        }

        binding.btnLottery.setOnClickListener {
            justStartActivity(LotteryDemoActivity::class.java)
        }

    }
}