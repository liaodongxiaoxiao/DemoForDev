package com.karl.demo.demo.app

import android.content.Context
import com.google.gson.GsonBuilder
import com.karl.demo.BuildConfig
import com.karl.kotlin.extension.getSPByKey
import com.karl.kotlin.extension.log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(logger: HttpLoggingInterceptor.Logger): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(logger)
        interceptor.level =
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        return interceptor
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptorLogger(): HttpLoggingInterceptor.Logger {
        return object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                message.log()
            }
        }
    }

    @Provides
    fun provideTokenInterceptorLogger(@ApplicationContext context: Context): Interceptor {
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request()
                request.url.toString().log()
                if (request.url.toString().contains("todo")) {
                    val builder: Request.Builder = request.newBuilder()
                        .addHeader(
                            "Content-Type",
                            "application/x-www-form-urlencoded; charset=UTF-8"
                        )
                        .addHeader("Accept-Encoding", "gzip, deflate")
                        .addHeader("Connection", "keep-alive")
                        .addHeader("Accept", "*/*")
                        .addHeader("Authorization", "Bearer ${context.getSPByKey("token")}")
                    return chain.proceed(builder.build())
                }
                return chain.proceed(request)
            }

        }
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor,tokenInterceptor:Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(tokenInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.MINUTES)
            .readTimeout(10, TimeUnit.MINUTES)
            .build()
    }

    /*@Provides
    @Singleton
    fun provideCallAdapter(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }*/


    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory {
        val gsonBuilder = GsonBuilder()
/*gsonBuilder.registerTypeAdapter(String::class.java, StringTypeAdapter("--"))
            .registerTypeAdapter(Double::class.java, DoubleTypeAdapter())
            .registerTypeAdapter(Double::class.javaPrimitiveType, DoubleTypeAdapter())
            .registerTypeAdapter(Int::class.java, IntTypeAdapter())
            .registerTypeAdapter(Int::class.javaPrimitiveType, IntTypeAdapter())
            .registerTypeAdapter(Char::class.javaPrimitiveType, CharTypeAdapter())
            .registerTypeAdapter(Boolean::class.java, BooleanTypeAdapter())
            .registerTypeAdapter(Boolean::class.javaPrimitiveType, BooleanTypeAdapter())
            .registerTypeAdapter(Char::class.java, CharTypeAdapter())*/



        gsonBuilder.serializeNulls()
        return GsonConverterFactory.create(gsonBuilder.create())
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        //callAdapterFactory: RxJava2CallAdapterFactory,
        converterFactory: GsonConverterFactory,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.109:8080/")
            //.addCallAdapterFactory(callAdapterFactory)
            .addConverterFactory(converterFactory)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideBaseAPI(retrofit: Retrofit): DemoAPI {
        return retrofit.create<DemoAPI>(
            DemoAPI::class.java
        )
    }

}
