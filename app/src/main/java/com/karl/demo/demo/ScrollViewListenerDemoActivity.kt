package com.karl.demo.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.karl.demo.R
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnScrollChangedListener
import com.karl.kotlin.extension.log
import com.karl.kotlin.extension.roundHalfUpStr
import kotlinx.android.synthetic.main.demo_activity_scroll_view_listener.*


class ScrollViewListenerDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_activity_scroll_view_listener)

        nsv.viewTreeObserver.addOnScrollChangedListener(OnScrollChangedListener {
            val view: View = nsv.getChildAt(nsv.childCount - 1) as View
            val height = nsv.height
            val sy = nsv.scrollY
            val bottom = view.bottom

            val diff: Int = bottom - (height + sy)
            if (diff == 0) {
                btn_yes.isEnabled = true
            }


            val scrollPosition =( (height + sy)* 100.0 / view.bottom )
            tv_pr.text = "${scrollPosition.roundHalfUpStr(2)}%"
            //pb.setProgress(scrollPosition.toInt(),true)
            pb.progress = scrollPosition.toInt()
        })
    }
}