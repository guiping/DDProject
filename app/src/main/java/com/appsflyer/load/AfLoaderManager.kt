package com.appsflyer.load

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleCoroutineScope
import com.dsauufysncia.ai.entity.BusEvent
import com.dsauufysncia.ai.net.UrlUtils
import com.dsauufysncia.ai.ui.fragment.PrivacyDialog
import com.dsauufysncia.ai.utils.BusAction
import com.dsauufysncia.ai.utils.RxBbs
import com.dsauufysncia.ai.utils.SpCacheUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import okio.IOException

object AfLoaderManager {
    fun initAfLoader(
        activity: Activity,
        lifecycle: LifecycleCoroutineScope,
        fragmentManager: FragmentManager,
        ponseListener: PonseListener
    ) {
        lifecycle.launch(Dispatchers.IO) {
            val client = OkHttpClient.Builder().followRedirects(false).build()
            val request =
                Request.Builder().url(UrlUtils.BASE_LOADER_URL + UrlUtils.LOADER_URL).build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                      lifecycle.launch(Dispatchers.Main) {
                        Intent(activity, com.dsauufysncia.ai.MainActivity::class.java).apply {
                            activity.startActivity(this)
                            activity.finish()
                        }
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    val code = response.code
                    val redirectUrl = response.header("Location")
                      if (code == 302) {
                        val redirectUrl = response.header("Location")
                        ponseListener.responseListener(true, redirectUrl)
                        lifecycle.launch(Dispatchers.Main) {
                            Intent(activity, AfLoaderActivity::class.java).apply {
                                putExtra("url", redirectUrl)
                                activity.startActivity(this)
                                activity.finish()
                            }
                        }
                    } else {
                        val isShowPrivacy =
                            SpCacheUtils.getString(activity, "isShowPrivacy", "0")
                        lifecycle.launch(Dispatchers.Main) {
                            if (isShowPrivacy == "1") {
                                Intent(
                                    activity,
                                    com.dsauufysncia.ai.MainActivity::class.java
                                ).apply {
                                    activity.startActivity(this)
                                    activity.finish()
                                }
                            } else {
                                SpCacheUtils.saveString(activity, "isShowPrivacy", "1")
                                val privacyDialog = PrivacyDialog()
                                if (privacyDialog.isAdded && privacyDialog.isVisible) {
                                    privacyDialog.dismiss()
                                }
                                privacyDialog.isJump = true
                                privacyDialog.show(fragmentManager, "PrivacyDialog")
                            }
                        }
                    }
                }
            })
        }
    }
}