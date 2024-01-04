package com.kiudysng.ddproject.net

import com.kiudysng.ddproject.entity.*
import io.reactivex.rxjava3.core.Observable

object AiApiImp {
    fun getAspectRatios(): Observable<AspectRatiosEntity> {
        return HttpManager.getApiClient().getAspectRatios()
    }

    fun getTemplateStyle(): Observable<TemplateStyleEntity> {
        return HttpManager.getApiClient().getTemplateStyle()
    }

    fun createTask(token:String,body: CreteTaskBodyEntity): Observable<CreateTaskEntity> {
        return HttpManager.getApiClient().createTask(token,"android-3.1.4",body)
    }

    fun taskBatch(ids: String): Observable<TasksBatchEntity> {
        return HttpManager.getApiClient().taskBatch(ids)
    }
    fun getToken(): Observable<TokenEntity> {
        return HttpManager.getApiClient().getToken("AIzaSyDxCoSRCFvdsYcJalNfBQQfGl0-YycRkdE")
    }
}