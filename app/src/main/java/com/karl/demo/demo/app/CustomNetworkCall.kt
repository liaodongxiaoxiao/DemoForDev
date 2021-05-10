package com.karl.demo.demo.app


sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Failed(val code: String, val error: String? = null) : ResultWrapper<Nothing>()
}


public suspend fun <R> request(block: suspend () -> BaseResponse<R>): ResultWrapper<R> {
    return try {
        val result = block.invoke()
        if (result.status == 200) {
            ResultWrapper.Success(result.data)
        } else {
            ResultWrapper.Failed(result.status.toString(), result.message)
        }
    } catch (e: Throwable) {
        e.printStackTrace()
        ResultWrapper.Failed("5050", e.message)
    }
}