package com.appsflyer.load

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.appsflyer.AFInAppEventParameterName
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

object AfUtils {

    fun openWebView(activity: Activity, url: String) {
        val intent = Intent(activity, AfLoaderActivity::class.java)
        intent.putExtra("url", url)
        activity.startActivityForResult(intent, 1002)
    }
    fun logEvent(activity: Activity, tag: String, value: String) {
        var eventValues: MutableMap<String?, Any?>

        eventValues = HashMap()
        eventValues[AFInAppEventParameterName.CONTENT_ID] = tag
        eventValues[AFInAppEventParameterName.CONTENT_TYPE] = tag
        eventValues[AFInAppEventParameterName.CONTENT] = value
        try {
            val jsonObject = JSONObject(value)
            val amount = jsonObject.optString("amount")
            eventValues[AFInAppEventParameterName.REVENUE] = amount
            eventValues[AFInAppEventParameterName.CURRENCY] = "PHP"

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        AppsFlyerLib.getInstance()
            .logEvent(activity, tag, eventValues, object : AppsFlyerRequestListener {
                override fun onSuccess() {

                }
                override fun onError(i: Int, s: String) {

                }
            })
    }
    val EVENTS = arrayOf(
        "firstrecharger",
        "login",
        "logout",
        "registerClick",
        "rechargeClick", "register",
        "recharge", "withdrawClick", "withdrawOrderSuccess", "firstrecharge"
    )
    fun needSendFlyerEvent(event: String): Boolean {
        return listOf(*EVENTS).contains(event)
    }
}