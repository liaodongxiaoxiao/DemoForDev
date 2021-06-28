package com.karl.demo.demo.app

import com.karl.demo.demo.entity.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface DemoAPI {
    @GET("ktorAPI/common/division/{name}")
    suspend fun getDivision(@Path("name") name: String): BaseResponse<MutableList<DivisionEntity>>

    @GET("ktorAPI/weather/{code}")
    suspend fun getCityWeatherByCityCode(@Path("code") code: String): BaseResponse<WeatherEntity>


    @POST("ktorAPI/user/login")
    suspend fun login(@Body body: RequestBody): BaseResponse<LoginResult>

    @POST("ktorAPI/user/upload")
    @Multipart
    suspend fun uploadImage(
        @Part part: MultipartBody.Part,
        @Part("userId") userId: String
    ): BaseResponse<CommonEntity>

    @GET("ktorAPI/user/{userId}")
    suspend fun getUserInfo(@Path("userId") userId: String): BaseResponse<User>

    @GET("ktorAPI/user/check/{userName}")
    suspend fun checkUserNameExist(@Path("userName") userName: String): BaseResponse<CommonEntity>

    //@GET("app/checkUpdate/{versionCode}")
    //suspend fun checkUpdate(@Path("versionCode") versionCode: Int): BaseResponse<CheckUpdateResult>

    @GET("ktorAPI/user/checknick/{userName}")
    suspend fun checkNickNameExist(@Path("userName") name: String): BaseResponse<CommonEntity>

    @POST("ktorAPI/user/signin")
    suspend fun register(@Body body: RequestBody): BaseResponse<CommonEntity>
}