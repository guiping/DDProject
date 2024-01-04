package com.kiudysng.ddproject.net

import com.kiudysng.ddproject.entity.*
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface AiApiService {

    @GET(UrlUtils.ASPECT_RATIOS_1)
    fun getAspectRatios(): Observable<AspectRatiosEntity>

    @GET(UrlUtils.GET_STYLES_1)
    fun getTemplateStyle(): Observable<TemplateStyleEntity>

    @POST(UrlUtils.CREATE_TASK_1)
    fun createTask(
        @Header("Authorization") token: String,
        @Header("x-app-version") app_version: String,
        @Body body: CreteTaskBodyEntity
    ): Observable<CreateTaskEntity>

    @POST(UrlUtils.GET_TOKEN_1)
    fun getToken(
        @Query("key") key: String
    ): Observable<TokenEntity>

    @GET(UrlUtils.TASK_BATCH_1)
    fun taskBatch(@Query("ids") ids: String): Observable<TasksBatchEntity>
}