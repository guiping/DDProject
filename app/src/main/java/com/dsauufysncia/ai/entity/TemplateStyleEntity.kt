package com.dsauufysncia.ai.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

class TemplateStyleEntity : ArrayList<TemplateStyleEntityItem>()

@Entity(tableName = "tab_template_style")
data class TemplateStyleEntityItem(
    @PrimaryKey(autoGenerate = true) val id_key: Int,
    val created_at: String?,
    val deleted_at: String?,
    val id: Int,
    val is_new: Boolean,
    val is_premium: Boolean,
    val is_visible: Boolean,
    val model_type: String?,
    val name: String?,
    val photo_url: String?,
    val updated_at: String?
):java.io.Serializable