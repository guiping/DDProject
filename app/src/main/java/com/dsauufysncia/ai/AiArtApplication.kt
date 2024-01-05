package com.dsauufysncia.ai

import android.app.Application
import android.content.Context

class AiArtApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        com.dsauufysncia.ai.AiArtApplication.Companion.appContext = this
    }
    companion object {
        lateinit var appContext: Context
            private set
    }
}