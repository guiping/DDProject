package com.dsauufysncia.ai.utils

import android.content.Context
import android.content.SharedPreferences

object SpCacheUtils {
    private const val PREFERENCES_NAME = "ai_art_img_preferences"


    fun saveString(context: Context, key: String, value: String) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(context: Context, key: String, defaultValue: String): String? {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.getString(key, defaultValue)
    }


    /**
     * 获取 SharedPreferences 对象
     *
     * @param context 上下文对象
     * @return SharedPreferences 对象
     */
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
}