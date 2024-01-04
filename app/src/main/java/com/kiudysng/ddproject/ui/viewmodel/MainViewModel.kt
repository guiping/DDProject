package com.kiudysng.ddproject.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiudysng.ddproject.AiArtApplication
import com.kiudysng.ddproject.db.AiDbBase
import com.kiudysng.ddproject.entity.BusEvent
import com.kiudysng.ddproject.entity.CreteTaskBodyEntity
import com.kiudysng.ddproject.entity.TasksBatchEntityItem
import com.kiudysng.ddproject.net.AiApiImp
import com.kiudysng.ddproject.utils.BusAction
import com.kiudysng.ddproject.utils.RxBbs
import com.kiudysng.ddproject.utils.SpCacheUtils
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainViewModel : ViewModel() {
    val dao = AiDbBase.getDb(AiArtApplication.appContext).dao()
    val loadDialogState = MutableLiveData<Boolean>()
    fun getData() {  //获取数据

        viewModelScope.launch(Dispatchers.IO) {
            if (dao.getTemplateStyle().isEmpty()) {
                loadDialogState.postValue(false)

                val observable = AiApiImp.getTemplateStyle().subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io()).subscribe({

                        loadDialogState.postValue(true)
                        if (!it.isEmpty()) {
                            viewModelScope.launch(Dispatchers.IO) {
                                dao.insertTemplateStyle(it)
                                //发送Post通知UI层刷新
                                RxBbs.postEvent(BusEvent(BusAction.BUS_ACTION_TEMPLATE_STYLE_SUCCESS))
                            }
                        }
                    }, {
                        Log.e("pLog", " getTemplateStyle请求失败")
                        loadDialogState.postValue(true)
                    })
            }
            if (dao.getAspectRatios().isEmpty()) {
                val observable1 = AiApiImp.getAspectRatios().subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io()).subscribe({

                        if (!it.isEmpty()) {
                            viewModelScope.launch(Dispatchers.IO) {
                                dao.insertAspectRatio(it)
                                RxBbs.postEvent(BusEvent(BusAction.BUS_ACTION_ASPECT_RATIONS_SUCCESS))
                            }
                        }
                    }, { Log.e("pLog", "getAspectRatios 请求失败") })
            }

            val observable2 = AiApiImp.getToken().subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io()).subscribe({
                    Log.e("pLog", "getToken 请求成功  ${it.idToken}")
                    SpCacheUtils.saveString(AiArtApplication.appContext, "token", it.idToken)
                }, { Log.e("pLog", "getToken 请求失败 ${it.message}") })
        }
    }


    val batchTaskSuccess = MutableLiveData<TasksBatchEntityItem>()
    fun createTask(body: CreteTaskBodyEntity) {
        val token =
            "Bearer ${SpCacheUtils.getString(AiArtApplication.appContext, "token", "")}"  //创建token
        //创建Task 任务
        viewModelScope.launch(Dispatchers.IO) {
            val observable = AiApiImp.createTask(token, body).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io()).subscribe({
                    Log.e("pLog", "createTask 请求成功  ${it.id}")
                    taskBatch(it.id)
                }, {
                    Log.e("pLog", "createTask 请求失败")
                })
        }
    }

    fun taskBatch(id: String) {
        viewModelScope.launch(Dispatchers.IO) {

            val observable = AiApiImp.taskBatch(id).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io()).subscribe({
                    Log.e("pLog", "taskBatch 请求成功  ${it.size}")
                    if (!it.isEmpty()) {
                        val tasksBatchEntityItem = it[0]
                        if (tasksBatchEntityItem.state == "completed") {
                            batchTaskSuccess.postValue(tasksBatchEntityItem)
                        } else {
                            runBlocking {
                                delay(kotlin.random.Random.nextLong(3500, 5000))
                                taskBatch(id)   //继续请求
                            }
                        }
                    }

                }, {
                    Log.e("pLog", "taskBatch 请求失败")
                })
        }
    }
}