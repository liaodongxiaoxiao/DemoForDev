package com.karl.demo.demo.model

import com.karl.demo.demo.app.BaseResponse
import com.karl.demo.demo.app.DemoAPI
import com.karl.demo.demo.entity.DivisionEntity
import javax.inject.Inject

class DivisionModel @Inject constructor(
    private val api: DemoAPI
) {
    suspend fun getDivisionByName(name: String): BaseResponse<MutableList<DivisionEntity>> {
        return api.getDivision(name)
    }
}