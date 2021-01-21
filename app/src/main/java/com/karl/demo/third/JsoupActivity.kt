package com.karl.demo.third

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karl.demo.R
import com.karl.kotlin.extension.log
import kotlinx.android.synthetic.main.activity_3_jsoup.*
import org.jsoup.Jsoup
import kotlin.concurrent.thread

class JsoupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_3_jsoup)

        btn_test.setOnClickListener {
            thread {
                val document = Jsoup
                    .connect("https://www.ikanwzd.top/chapter/596")
                    .userAgent("Mozilla/5.0 (Linux; U; Android 4.0.3; ko-kr; LG-L160L Build/IML74K) AppleWebkit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30")
                    .get()
                document.log()
            }
        }
    }
}