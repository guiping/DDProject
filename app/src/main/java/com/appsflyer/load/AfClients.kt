package com.appsflyer.load

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.net.http.SslError
import android.os.Message
import android.util.Log
import android.webkit.*

class AfClients (var activity: Activity, var webView: WebView) :
    WebChromeClient() {
    var newWebView: WebView? = null
    private val TAB = "ChromeClients"
    override fun onJsAlert(view: WebView, url: String, message: String, result: JsResult): Boolean {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage(message)
            .setPositiveButton("OK") { arg0: DialogInterface, arg1: Int -> arg0.dismiss() }.show()
        result.cancel()
        return true
    }

    override fun onCreateWindow(
        view: WebView,
        isDialog: Boolean,
        isUserGesture: Boolean,
        resultMsg: Message
    ): Boolean {

        newWebView = WebView(view.context)
// 设置 WebView 属性，如加载方式、JavaScript 支持等
        newWebView?.settings?.javaScriptEnabled = true
        newWebView?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                  AfUtils.openWebView(activity, request.url.toString())
                return true
            }

            override fun onReceivedSslError(
                view: WebView,
                handler: SslErrorHandler,
                error: SslError
            ) {
                handler.proceed()
            }
        }
        val transport = resultMsg.obj as WebView.WebViewTransport
        transport.webView = newWebView
        resultMsg.sendToTarget()
        return true
    }
}