package com.kiudysng.ddproject.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kiudysng.ddproject.entity.AspectRatiosEntityItem
import com.kiudysng.ddproject.entity.TemplateStyleEntityItem

@Dao
interface AiDbDao {
    @Insert
    suspend fun insertTemplateStyle(templateStyles: ArrayList<TemplateStyleEntityItem>)
    @Query("select * from tab_template_style LIMIT 60")
    suspend fun getTemplateStyle(): List<TemplateStyleEntityItem>

    @Insert
    suspend fun insertAspectRatio(aspectRatio: ArrayList<AspectRatiosEntityItem>)

    @Query("select * from tab_aspect_ratios")
    suspend fun getAspectRatios(): List<AspectRatiosEntityItem>
}