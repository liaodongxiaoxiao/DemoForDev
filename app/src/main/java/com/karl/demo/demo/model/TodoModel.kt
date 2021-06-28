package com.karl.demo.demo.model

import com.karl.demo.demo.app.BaseResponse
import com.karl.demo.demo.app.DemoAPI
import com.karl.demo.demo.entity.LoginResult
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import javax.inject.Inject

class TodoModel @Inject constructor(private val api: DemoAPI) {
    suspend fun login(name: String, password: String): BaseResponse<LoginResult> {
        val jsonParams: MutableMap<String?, Any?> =
            mutableMapOf("userName" to name, "password" to password)

        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )
        return api.login(body)
    }
}