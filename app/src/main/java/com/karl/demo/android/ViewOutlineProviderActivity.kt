package com.karl.demo.android

import android.graphics.Outline
import android.graphics.Path
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.karl.demo.R
import kotlinx.android.synthetic.main.activity_android_view_outline_provider.*


class ViewOutlineProviderActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_android_view_outline_provider)

        btn_set.setOnClickListener {

            tv_hi.setElevation(5f)
            tv_hi.outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline) {
                    //outline.setOval(0, 0, tv_hi.getWidth(), tv_hi.getHeight())
                    outline.setRoundRect(0, 0, tv_hi.getWidth(), tv_hi.getHeight(), 8.0f)
                }
            }
            tv_hi.setClipToOutline(true)
        }
    }
}