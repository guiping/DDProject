package com.kiudysng.ddproject.entity

class TasksBatchEntity : ArrayList<TasksBatchEntityItem>()

data class TasksBatchEntityItem(
    val created_at: String,
    val generated_photo_keys: List<String>,
    val id: String,
    val input_spec: InputSpec,
    val is_nsfw: Boolean,
    val photo_url_list: List<String>,
    val premium: Boolean,
    val result: Result,
    val state: String,
    val updated_at: String,
    val user_id: String
) : java.io.Serializable


data class Result(
    val `final`: String
)