package com.karl.demo.android

import android.graphics.Outline
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.RequiresApi
import com.karl.demo.BaseActivity
import com.karl.demo.R
import com.karl.demo.databinding.ActivityAndroidViewOutlineProviderBinding


class ViewOutlineProviderActivity : BaseActivity<ActivityAndroidViewOutlineProviderBinding>(
    ActivityAndroidViewOutlineProviderBinding::inflate
) {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_android_view_outline_provider)

        binding.btnSet.setOnClickListener {

            binding.tvHi.setElevation(5f)
            binding.tvHi.outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline) {
                    //outline.setOval(0, 0, tv_hi.getWidth(), tv_hi.getHeight())
                    outline.setRoundRect(
                        0,
                        0,
                        binding.tvHi.getWidth(),
                        binding.tvHi.getHeight(),
                        8.0f
                    )
                }
            }
            binding.tvHi.setClipToOutline(true)
        }
    }
}