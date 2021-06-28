package com.karl.demo.demo

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.Spanned
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.karl.demo.R
import kotlinx.android.synthetic.main.activity_html_demo.*

class HtmlDemoActivity : AppCompatActivity() {
    private val html1 =
        "<p style='font-size: 0;'><span style='color: rgb(102, 102, 102);font-size: 14px;'><strong>不发货地区</strong></span><span style='color: rgb(153, 153, 153);'><strong>：</strong></span><span style='color: rgb(209, 72, 65);font-size: 14px;'>新疆，西藏，港澳台地区，山西，陕西，山东，河南，河北，黑龙江。</span></p>"
    private val html2 =
        "<p><span style=\"color: rgb(102, 102, 102);\"><strong>不发货地区</strong></span><span style=\"color: rgb(153, 153, 153);\"><strong>：</strong></span><span style=\"color: rgb(209, 72, 65);\">新疆，西藏，港澳台地区，山西，陕西，山东，河南，河北，黑龙江。</span></p>"
  private val html3 =
        "<span><p style='margin: 0;display: inline-block;'><span style=\"color: rgb(102, 102, 102);\"><strong>不发货地区</strong></span><span style=\"color: rgb(153, 153, 153);\"><strong>：</strong></span><span style=\"color: rgb(209, 72, 65);\">新疆，西藏，港澳台地区，山西，陕西，山东，河南，河北，黑龙江。</span></p></span>"

    private val html4 ="<p style='margin: 0;display: inline-block;'><strong><strong><span style=\"color: rgb(102, 102, 102);\">发货时间：</span></strong></strong><strong><span style=\"color: rgb(235, 107, 86);\">早7点到晚9点。</span></strong></p>"
    private val html5 ="""
        
        <p style="margin:0;">
            1.我是我是左侧面内容我是左侧面内容我是左侧面内容我是左侧面内容我是左侧面内容
        </p >
        <p style="margin:0;">
            2.我是我是左侧面内容我是左侧面内容我是左侧面内容我是左侧面内容我是左侧面内容
        </p >
    """.trimIndent()

    private val html6="""
        <p style='margin: 0;display: inline-block;padding:0;'><span style="color: rgb(102, 102, 102);"><strong>不发货地区</strong></span><span style="color: rgb(153, 153, 153);"><strong>：</strong></span><span style="color: rgb(209, 72, 65);">新疆，西藏，港澳台地区。</span></p>
    """.trimIndent()

    private val info="""
        <ol ><li><span style=\"font-family:宋体;font-size:12.0pt;\">配送范围：中国大陆地区（生鲜类商品受疫情管控部分地区暂不发货，不同商品配送范围会有所差异，以商品详情为准）</span></li><li><span style=\"font-family:宋体;font-size:12.0pt;\">运费说明：</span></li><li><span style=\"font-family:宋体;font-size:12.0pt;\">日系好物<span style=\"font-family:宋体;font-size:12.0pt;\">/</span>文创精选</span><span style=\"font-family:宋体;font-size:12.0pt;\">专区的商品包邮；</span></li><li><span style=\"font-family:宋体;font-size:12.0pt;\">收到商品之日起七天内因质量问题退货而产生的运费由卖家承担，退货快递支持圆通、神通、中通、韵达，<strong style=\"font-family:宋体;font-size:12.0pt;\">不支持货到付款</strong>；</span></li><li><span style=\"font-family:宋体;font-size:12.0pt;\">运费不单独开具发票。</span></li><li><span style=\"font-family:宋体;font-size:12.0pt;\">退换货须知：</span></li><li><span style=\"font-family:宋体;font-size:12.0pt;\">收到商品七日内可申请退货：收到商品之日起算的七天内，用户可以通过<span style=\"font-family:宋体;font-size:12.0pt;\">APP</span>在线客服申请退货；自申请之日起七个工作日内将退积分或退款到兑换账户；（质量问题不包括人为破坏或因使用不当造成的破损、变形、染色等问题）</span></li><li><span style=\"font-family:宋体;font-size:12.0pt;\">食品类商品请在签收后第一时间检查，如果商品有腐坏、破损等情况，请把商品和快递面单一起拍照并在<span style=\"font-family:宋体;font-size:12.0pt;\">24</span>小时内联系客服，不能提供符合要求的照片或未能在<span style=\"font-family:宋体;font-size:12.0pt;\">24</span>小时内联系客服，将不予退货、退款处理</span></li><li><span style=\"font-family:宋体;font-size:12.0pt;\">发票问题：如需开具发票，可在线联系客服申请发票，仅支持现金部分开具发票；</span></li><li><span style=\"font-family:宋体;font-size:12.0pt;\">如何联系我们：<span style=\"font-family:宋体;font-size:12.0pt;\">APP</span>在线客服 &nbsp;周一至周日 <span style=\"font-family:宋体;font-size:12.0pt;\">09:00-19:00</span></span></li></ol>
    """.trimIndent()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_html_demo)

        //tv1.text = getSpan(html5)
        wv1.setBackgroundColor(Color.TRANSPARENT)
        wv2.setBackgroundColor(Color.TRANSPARENT)
        wv1.loadData(html1, "text/html", "UTF-8")
        wv2.loadData(getPageHtml(info), "text/html", "UTF-8")
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

    private fun getPageHtml(body:String):String{
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