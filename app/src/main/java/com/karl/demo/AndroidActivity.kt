package com.karl.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karl.demo.android.ViewOutlineProviderActivity
import kotlinx.android.synthetic.main.activity_android.*

class AndroidActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_android)

        btn_view_outline_provider.setOnClickListener {
            startActivity(Intent(this, ViewOutlineProviderActivity::class.java))
        }
    }
}