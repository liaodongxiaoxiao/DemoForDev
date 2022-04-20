package com.karl.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karl.demo.android.MotionLayoutActivity
import com.karl.demo.android.PopWindowActivity
import com.karl.demo.android.PreferencesDataStoreActivity
import com.karl.demo.android.ViewOutlineProviderActivity
import com.karl.demo.databinding.ActivityAndroidBinding
import com.karl.demo.extesion.justStartActivity

class AndroidActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAndroidBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAndroidBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnViewOutlineProvider.setOnClickListener {
            startActivity(Intent(this, ViewOutlineProviderActivity::class.java))
        }
        binding.btnMotionLayout.setOnClickListener {
            startActivity(Intent(this, MotionLayoutActivity::class.java))
        }

        binding.btnPreferencesDataStore.setOnClickListener {
            justStartActivity(PreferencesDataStoreActivity::class.java)
        }

        binding.btnPop.setOnClickListener {
            justStartActivity(PopWindowActivity::class.java)
        }
    }
}