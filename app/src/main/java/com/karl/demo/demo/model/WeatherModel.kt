package com.karl.demo.demo.model

import com.karl.demo.demo.app.BaseResponse
import com.karl.demo.demo.app.DemoAPI
import com.karl.demo.demo.entity.WeatherEntity
import javax.inject.Inject

class WeatherModel @Inject constructor(private val api: DemoAPI) {
    suspend fun getCityWeatherByCityCode(code: String): BaseResponse<WeatherEntity> {
        return api.getCityWeatherByCityCode(code)
    }
}