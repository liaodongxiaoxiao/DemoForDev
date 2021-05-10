package com.karl.demo.demo.app

import com.karl.demo.demo.entity.DivisionEntity
import com.karl.demo.demo.entity.WeatherEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface DemoAPI {
    @GET("ktorAPI/common/division/{name}")
    suspend fun getDivision(@Path("name") name: String): BaseResponse<MutableList<DivisionEntity>>

    @GET("ktorAPI/weather/{code}")
    suspend fun getCityWeatherByCityCode(@Path("code") code: String): BaseResponse<WeatherEntity>
}