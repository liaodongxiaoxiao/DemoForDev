package com.karl.demo.demo

import android.graphics.Color
import android.os.Bundle
import com.karl.demo.BaseActivity
import com.karl.demo.R
import com.karl.demo.databinding.ActivityHtmlDemoBinding

class HtmlDemoActivity : BaseActivity<ActivityHtmlDemoBinding>(ActivityHtmlDemoBinding::inflate) {
    private val html1 =
        "<p style='font-size: 0;'><span style='color: rgb(102, 102, 102);font-size: 14px;'><strong>不发货地区</strong></span><span style='color: rgb(153, 153, 153);'><strong>：</strong></span><span style='color: rgb(209, 72, 65);font-size: 14px;'>新疆，西藏，港澳台地区，山西，陕西，山东，河南，河北，黑龙江。</span></p>"
    private val html2 =
        "<p><span style=\"color: rgb(102, 102, 102);\"><strong>不发货地区</strong></span><span style=\"color: rgb(153, 153, 153);\"><strong>：</strong></span><span style=\"color: rgb(209, 72, 65);\">新疆，西藏，港澳台地区，山西，陕西，山东，河南，河北，黑龙江。</span></p>"
    private val html3 =
        "<span><p style='margin: 0;display: inline-block;'><span style=\"color: rgb(102, 102, 102);\"><strong>不发货地区</strong></span><span style=\"color: rgb(153, 153, 153);\"><strong>：</strong></span><span style=\"color: rgb(209, 72, 65);\">新疆，西藏，港澳台地区，山西，陕西，山东，河南，河北，黑龙江。</span></p></span>"

    private val html4 =
        "<p style='margin: 0;display: inline-block;'><strong><strong><span style=\"color: rgb(102, 102, 102);\">发货时间：</span></strong></strong><strong><span style=\"color: rgb(235, 107, 86);\">早7点到晚9点。</span></strong></p>"
    private val html5 = """
        
        <p style="margin:0;">
            1.我是我是左侧面内容我是左侧面内容我是左侧面内容我是左侧面内容我是左侧面内容
        </p >
        <p style="margin:0;">
            2.我是我是左侧面内容我是左侧面内容我是左侧面内容我是左侧面内容我是左侧面内容
        </p >
    """.trimIndent()

    private val html6 = """
        <p style='margin: 0;display: inline-block;padding:0;'><span style="color: rgb(102, 102, 102);"><strong>不发货地区</strong></span><span style="color: rgb(153, 153, 153);"><strong>：</strong></span><span style="color: rgb(209, 72, 65);">新疆，西藏，港澳台地区。</span></p>
    """.trimIndent()

    private val info = """
        
    """.trimIndent()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_html_demo)

        //tv1.text = getSpan(html5)
        binding.apply {
            wv1.setBackgroundColor(Color.TRANSPARENT)
            wv2.setBackgroundColor(Color.TRANSPARENT)
            wv1.loadData(html1, "text/html", "UTF-8")
            wv2.loadData(getPageHtml(info), "text/html", "UTF-8")
        }

        /*val params: ViewGroup.LayoutParams = v2.layoutParams
        val paramsWV2 = wv2.layoutParams
        params.height = paramsWV2.height-60
        v2.layoutParams = params*/
    }

    /*private fun getSpan(text: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
        } else {
            HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT)
        }
    }*/

    private fun getPageHtml(body: String): String {
        return """
            <html>
            <head>
                <style type="text/css"> 
                    html, body { width:100%; height: 100%; margin: 0px; padding: 0px; } 
                </style>
            </head>
            <body>$body</body>
            </html>
        """.trimIndent()
    }
}