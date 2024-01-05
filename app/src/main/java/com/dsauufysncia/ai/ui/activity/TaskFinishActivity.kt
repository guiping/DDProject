package com.dsauufysncia.ai.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dsauufysncia.ai.databinding.ActivityFinishTaskBinding
import com.dsauufysncia.ai.utils.AiUtils
import com.dsauufysncia.ai.utils.DownloadListener
import com.dsauufysncia.ai.utils.GlideUtils

class TaskFinishActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFinishTaskBinding
    var imgUrl: String? = null
    var msgInfo: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
        if (bundle == null) finish()
        binding.apply {
            bundle?.let {
                imgUrl = it.getString("batch_img_url")
                msgInfo = it.getString("batch_msg_info")

            }
            imgUrl?.let {
                GlideUtils.loadImage(this@TaskFinishActivity, it, ivTempPic)
            }
            ivBack.setOnClickListener { finish() }
            tvDownloadTask.setOnClickListener {
                flLoad.visibility = View.VISIBLE
                AiUtils.saveImageToGallery(
                    this@TaskFinishActivity,
                    imgUrl!!,
                    object : DownloadListener {
                        override fun downloadSuccess() {
                            runOnUiThread {
                                flLoad.visibility = View.GONE
                                Toast.makeText(
                                    this@TaskFinishActivity,
                                    "download success",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }

                    })
            }
        }
    }
}