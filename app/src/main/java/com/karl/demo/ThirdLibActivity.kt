package com.karl.demo

import android.content.Intent
import android.os.Bundle
import com.karl.demo.databinding.ActivityThirdLibBinding
import com.karl.demo.extesion.justStartActivity
import com.karl.demo.third.FlexboxLayoutActivity
import com.karl.demo.third.JsoupActivity
import com.karl.demo.third.MMKVActivity
import com.karl.demo.third.SmartRefreshLayoutActivity

class ThirdLibActivity : BaseActivity<ActivityThirdLibBinding>(ActivityThirdLibBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnFlexBoxLayout.setOnClickListener {
            //google flexbox layout
            startActivity(Intent(this, FlexboxLayoutActivity::class.java))
        }
        binding.btnJsoup.setOnClickListener {
            //jsoup
            startActivity(Intent(this, JsoupActivity::class.java))
        }

        binding.btnMmkv.setOnClickListener {
            justStartActivity(MMKVActivity::class.java)
        }

        binding.btnRefresh.setOnClickListener {
            justStartActivity(SmartRefreshLayoutActivity::class.java)
        }

    }
}