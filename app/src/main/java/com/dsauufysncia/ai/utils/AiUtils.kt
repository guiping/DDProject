package com.dsauufysncia.ai.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentValues
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.TypedValue
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object AiUtils {
    fun dpToPx(dp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            Resources.getSystem().displayMetrics
        )
    }

    fun spToPx(sp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            sp,
            Resources.getSystem().displayMetrics
        )
    }

    fun pxToSp(px: Float): Float {
        val fontScale = Resources.getSystem().displayMetrics.scaledDensity
        return (px / fontScale + 0.5f).toInt().toFloat()
    }

    fun copyToClipboard(context: Context, copyStr: String?) {
        // 获取剪贴板管理器
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", copyStr)
        clipboardManager.setPrimaryClip(clipData)
    }

    fun saveImageToGallery(context: Context, imageUrl: String, downloadListener: DownloadListener) {
        val request = Request.Builder()
            .url(imageUrl)
            .build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val inputStream = response.body?.byteStream()
                inputStream?.let {
                    saveImageToGallery(context, it)
                }
                downloadListener.downloadSuccess()

            }

            override fun onFailure(call: Call, e: IOException) {
                // 处理请求失败的情况
                downloadListener.downloadSuccess()
            }
        })
    }

    private fun saveImageToGallery(context: Context, inputStream: InputStream) {
        val displayName = "${System.currentTimeMillis()}.jpg" // 生成唯一的文件名

        val resolver = context.contentResolver
        val imageCollectionUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        val imageDetails = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, displayName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis())
            put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
        }

        var imageUri: Uri? = null
        var outputStream: OutputStream? = null
        try {
            imageUri = resolver.insert(imageCollectionUri, imageDetails)
            outputStream = imageUri?.let { resolver.openOutputStream(it) }
            if (outputStream != null) {
                val buffer = ByteArray(4 * 1024) // 缓冲区大小
                var bytesRead = inputStream.read(buffer)
                while (bytesRead != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                    bytesRead = inputStream.read(buffer)
                }
                outputStream.flush()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            inputStream.close()
            outputStream?.close()
        }
    }

}
