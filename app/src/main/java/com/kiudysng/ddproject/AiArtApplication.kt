package com.kiudysng.ddproject

import android.app.Application
import android.content.Context

class AiArtApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
    companion object {
        lateinit var appContext: Context
            private set
    }
}