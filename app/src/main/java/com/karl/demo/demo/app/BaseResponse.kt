package com.karl.demo.demo.app


data class BaseResponse<T>(
    val `data`: T,
    val message: String,
    val status: Int
)
