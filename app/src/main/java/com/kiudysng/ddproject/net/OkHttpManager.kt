package com.kiudysng.ddproject.net

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object OkHttpManager {
    private const val TIME_OUT = 60L
    private val defaultOkHttpClient by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {

        val builder = OkHttpClient.Builder()
            .callTimeout(TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
        val loggingInterceptor = HttpLoggingInterceptor { message -> Log.i("okHttp1", message) }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(loggingInterceptor)

        builder.build()
    }

    fun getOkHttpClient(): OkHttpClient {
        val okHttpClient = getCustomOkHttpClient()
        if (null != okHttpClient) {
            return okHttpClient
        }
        return defaultOkHttpClient.newBuilder().addInterceptor(getCustomInterceptor()).build()
    }

    /**
     * 子类可以重写此方法，返回自定义的 OkHttpClient 对象
     */
    fun getCustomOkHttpClient(): OkHttpClient? {
        return null
    }

    /**
     * 子类可以重写此方法，返回自定义的 Interceptor 对象
     */
    fun getCustomInterceptor(): Interceptor = Interceptor { chain ->
        chain.proceed(chain.request())
    }
}