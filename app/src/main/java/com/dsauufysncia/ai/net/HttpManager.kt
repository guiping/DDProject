package com.dsauufysncia.ai.net

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object HttpManager {
    private val apiService: AiApiService
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(UrlUtils.BASE_URL)
            .client(OkHttpManager.getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create()) // 添加 RxJava 3 的适配器
            .build()
        apiService = retrofit.create(AiApiService::class.java)
    }

    fun getApiClient(): AiApiService {
        return apiService
    }
}