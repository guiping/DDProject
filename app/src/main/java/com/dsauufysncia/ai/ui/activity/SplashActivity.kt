package com.dsauufysncia.ai.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.appsflyer.AppsFlyerLib
import com.appsflyer.load.AfLoaderManager
import com.appsflyer.load.PonseListener
import com.dsauufysncia.ai.R
import com.dsauufysncia.ai.utils.BusAction
import com.dsauufysncia.ai.utils.RxBbs

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        AppsFlyerLib.getInstance().init("QTgEqBhqcSpJkyef7PSu7g", null, applicationContext)
        AppsFlyerLib.getInstance().start(this)
        AfLoaderManager.initAfLoader(
            this@SplashActivity,
            lifecycleScope,
            supportFragmentManager,
            object :
                PonseListener {
                override fun responseListener(success: Boolean, url: String?) {

                }
            })
        RxBbs.observeEvents(this) {
            when (it.action) {
                BusAction.BUS_ACTION_FINISH_SPLASH -> {
                    finish()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBbs.removeEvent(this)
    }
}