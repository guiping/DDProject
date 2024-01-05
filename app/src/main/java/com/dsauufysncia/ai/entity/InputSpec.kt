package com.dsauufysncia.ai.entity

data class InputSpec(
    val aspect_ratio: String,
    val aspect_ratio_height: Int,
    val aspect_ratio_width: Int,
    val gen_type: String,
    val prompt: String,
    val style: Int
): java.io.Serializable
