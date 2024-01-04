package com.kiudysng.ddproject.ui.activity

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.kiudysng.ddproject.databinding.ActivityWebviewBinding

class LoadWebActivity:AppCompatActivity() {
    private lateinit var binding: ActivityWebviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.run {
            val webSettings: WebSettings = webviewLoad.settings
            webSettings.domStorageEnabled = true
            webSettings.blockNetworkImage = false
            webSettings.defaultTextEncodingName = "UTF-8"
            webSettings.useWideViewPort = true
            webSettings.loadWithOverviewMode = true
            webSettings.javaScriptEnabled = true
            webviewLoad.webViewClient = WebViewClient()
            webSettings.setSupportMultipleWindows(false)


            webviewLoad.loadUrl("https://sites.google.com/view/aeeviiso-privacy")


        }
    }
}