package com.kiudysng.ddproject.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.kiudysng.ddproject.entity.BusEvent

object RxBbs {
    private val eventLiveData = MutableLiveData<BusEvent>()

    fun postEvent(event: BusEvent) {
        eventLiveData.postValue(event)
    }

    fun observeEvents(owner: LifecycleOwner, observer: Observer<BusEvent>) {
        eventLiveData.observe(owner, observer)
    }
}