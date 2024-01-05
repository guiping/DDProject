package com.appsflyer.load

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

class AfLoaderActivity : AppCompatActivity() {
    var loaderView: WebView? = null
    var url: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loaderView = WebView(this)
        setContentView(loaderView)
        val lp = loaderView?.layoutParams as ViewGroup.LayoutParams
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT
        loaderView?.layoutParams = lp
        val bundle = intent.extras
        if (bundle != null) {
            url = bundle.getString("url")
        }
        init()
    }

    private fun init() {
        loaderView?.let { webView ->
            val webSettings: WebSettings = webView.settings
            webSettings.useWideViewPort = true
            webSettings.defaultTextEncodingName = "utf-8"
            webSettings.savePassword = true
            webSettings.loadWithOverviewMode = true
            webSettings.javaScriptEnabled = true
            webSettings.allowFileAccess = true
            webSettings.domStorageEnabled = true
            webSettings.setSupportMultipleWindows(true)
            webSettings.javaScriptCanOpenWindowsAutomatically = true
            webSettings.setSupportMultipleWindows(true)
            webSettings.allowFileAccess = true
            webView.isHorizontalScrollBarEnabled = false
            webView.isVerticalScrollBarEnabled = false
            webView.addJavascriptInterface(object : Any() {
                @JavascriptInterface
                fun postMessage(tag: String, value: String) {
                    when (tag) {
                        "openWindow" -> {
                            try {
                                val jsonObject = JSONObject(value)
                                val url1 = jsonObject.optString("url")
                                runOnUiThread{
                                    webView.loadUrl(url1)
                                }
                            } catch (e: Exception) {

                            }
                        }
                        "closeWindow" -> {
                            runOnUiThread {
                                if (webView.canGoBack()) {
                                    webView.goBack()
                                } else {
                                    finish()
                                }
                            }
                        }

                        else -> {
                            if (AfUtils.needSendFlyerEvent(tag)) {
                                AfUtils.logEvent(this@AfLoaderActivity, tag, value)
                            }
                        }
                    }
                }
            }, "jsBridge")
            webView.webChromeClient = AfClients(this, webView)
            webView.setDownloadListener { str, str2, str3, str4, j2 ->
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                this@AfLoaderActivity.startActivity(i)
                finish()
            }
            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                }

                override fun shouldOverrideUrlLoading(
                    view: WebView,
                    request: WebResourceRequest
                ): Boolean {
                    if (request.url.toString().startsWith("http") || request.url.toString()
                            .startsWith("https")
                    ) {
                        if (!request.url.toString()
                                .contains(
                                    AfEncryUtils.decryptAES(
                                        AfUrlUtils.loadApi1,
                                        AfUrlUtils.loadKey
                                    )
                                ) && !request.url.toString()
                                .contains(
                                    AfEncryUtils.decryptAES(
                                        AfUrlUtils.loadApi2,
                                        AfUrlUtils.loadKey
                                    )
                                )
                        ) {
                            webView?.loadUrl(request.url.toString())
                            return true
                        }

                        val i = Intent(Intent.ACTION_VIEW)
                        i.data = Uri.parse(request.url.toString())
                        this@AfLoaderActivity.startActivity(i)
                        return true
                    } else {
                        try {
                            Intent(Intent.ACTION_VIEW, Uri.parse(request.url.toString())).apply {
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                val chooser = Intent.createChooser(this, "Choose a browser")
                                startActivity(chooser)
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        return true
                    }
                }
            }
            webView.loadUrl(url!!)
        }
    }

    private fun finishWeb() {
        setResult(-2)
        finish()
    }

    override fun onBackPressed() {
        if (this.loaderView?.canGoBack() == true) {
            this.loaderView?.goBack()
        } else {
            finish()
        }
    }
}