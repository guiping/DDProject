package com.dsauufysncia.ai.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dsauufysncia.ai.entity.AspectRatiosEntityItem
import com.dsauufysncia.ai.entity.TemplateStyleEntityItem

@Database(entities = [AspectRatiosEntityItem::class, TemplateStyleEntityItem::class], version = 2, exportSchema = false)
abstract class AiDbBase :RoomDatabase(){
     abstract fun dao():AiDbDao

    companion object {
        @Volatile
        private var INSTANCE: AiDbBase? = null
        fun getDb(context: Context): AiDbBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AiDbBase::class.java,
                    "ai_art.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
            }

            return INSTANCE!!

        }
    }
}