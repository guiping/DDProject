package com.dsauufysncia.ai.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dsauufysncia.ai.entity.BusEvent

object RxBbs {
    private val eventLiveData = MutableLiveData<BusEvent>()

    fun postEvent(event: BusEvent) {
        eventLiveData.postValue(event)
    }

    fun observeEvents(owner: LifecycleOwner, observer: Observer<BusEvent>) {
        eventLiveData.observe(owner, observer)
    }
    fun removeEvent(owner: LifecycleOwner){
        eventLiveData.removeObservers(owner)
    }
}