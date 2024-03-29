package com.karl.demo

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import  androidx.viewbinding.ViewBinding

open class BaseActivity<B : ViewBinding>(val bindingFactory: (LayoutInflater) -> B): AppCompatActivity() {
    protected lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
    }
}