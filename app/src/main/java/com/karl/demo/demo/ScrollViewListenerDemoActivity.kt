package com.karl.demo.demo

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver.OnScrollChangedListener
import com.karl.demo.BaseActivity
import com.karl.demo.R
import com.karl.demo.databinding.DemoActivityScrollViewListenerBinding
import com.karl.kotlin.extension.roundHalfUpStr


class ScrollViewListenerDemoActivity :
    BaseActivity<DemoActivityScrollViewListenerBinding>(DemoActivityScrollViewListenerBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.demo_activity_scroll_view_listener)

        binding.apply {
            nsv.viewTreeObserver.addOnScrollChangedListener(OnScrollChangedListener {
                val view: View = nsv.getChildAt(nsv.childCount - 1) as View
                val height = nsv.height
                val sy = nsv.scrollY
                val bottom = view.bottom

                val diff: Int = bottom - (height + sy)
                if (diff == 0) {
                    btnYes.isEnabled = true
                }


                val scrollPosition = ((height + sy) * 100.0 / view.bottom)
                tvPr.text = "${scrollPosition.roundHalfUpStr(2)}%"
                //pb.setProgress(scrollPosition.toInt(),true)
                pb.progress = scrollPosition.toInt()
            })
        }


    }
}