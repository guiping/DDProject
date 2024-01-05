package com.appsflyer.load

interface PonseListener {
    fun responseListener(success: Boolean, url: String? = "")
}