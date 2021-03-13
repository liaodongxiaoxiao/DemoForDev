package com.karl.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karl.demo.android.MotionLayoutActivity
import com.karl.demo.android.PopWindowActivity
import com.karl.demo.android.PreferencesDataStoreActivity
import com.karl.demo.android.ViewOutlineProviderActivity
import com.karl.demo.extesion.justStartActivity
import kotlinx.android.synthetic.main.activity_android.*

class AndroidActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_android)

        btn_view_outline_provider.setOnClickListener {
            startActivity(Intent(this, ViewOutlineProviderActivity::class.java))
        }

        btn_motion_layout.setOnClickListener {
            startActivity(Intent(this, MotionLayoutActivity::class.java))
        }

        btn_preferences_data_store.setOnClickListener {
            justStartActivity(PreferencesDataStoreActivity::class.java)
        }

        btn_pop.setOnClickListener {
            justStartActivity(PopWindowActivity::class.java)
        }
    }
}