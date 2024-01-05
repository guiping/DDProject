package com.dsauufysncia.ai.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

class AspectRatiosEntity : ArrayList<AspectRatiosEntityItem>()
@Entity(tableName = "tab_aspect_ratios")
data class AspectRatiosEntityItem(
    @PrimaryKey(autoGenerate = true) val id_key:Int,
    val display_asset: String?,
    val display_name: String?,
    val height: Int,
    val is_default: Boolean,
    val is_premium: Boolean,
    val is_visible: Boolean,
    val type: String?,
    val width: Int
)