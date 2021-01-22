package com.karl.demo.third

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.karl.demo.R
import com.karl.kotlin.extension.log
import kotlinx.android.synthetic.main.activity_3_jsoup.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.lang.StringBuilder
import kotlin.concurrent.thread

class JsoupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_3_jsoup)

        btn_test.setOnClickListener {
            thread {
                val res = Jsoup
                    .connect("https://www.ikanwzd.top/chapter/596")
                    .userAgent("Mozilla/5.0 (Linux; U; Android 4.0.3; ko-kr; LG-L160L Build/IML74K) AppleWebkit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30")
                    .execute()
                val cookie = res.cookies()
                val doc: Document = res.parse()
                val title = doc.select("title").text().replace("在线阅读-久久漫画网", "")
                cookie.log()
                "title:$title".log()
                val es = doc.select("img.lazy")

                var imageURL = ""
                es.forEachIndexed { index, e ->
                    imageURL = e.attr("data-original")
                    println(imageURL)

                    getImage(imageURL, cookie)
                }
                //doc.log()

            }
        }

        v_layout
    }

    private fun getImage(imageUrl: String, cookies: Map<String, String>) {
        val imageVIew = ImageView(this)
        imageVIew.adjustViewBounds = true

        v_layout.addView(imageVIew)

        bindImageToView(cookies,imageVIew,imageUrl)

    }

    fun bindImageToView(cookies: Map<String, String>, view: ImageView, url: String) {
        val builder = StringBuilder()
        for ((key,value) in cookies){
            builder.append(key).append("=").append(value).append(";")
        }

        val cookie =
            GlideUrl(
                url,
                LazyHeaders.Builder().addHeader("cookie", builder.toString()).build()
            )
        Glide.with(this).load(cookie).into(view)
        //Logger.e("pic_id----->::$cookieName=$cookieValue ----->::$url")
    }
}