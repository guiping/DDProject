package com.dsauufysncia.ai.entity

data class CreateTaskEntity(  val created_at: String,
                              val generated_photo_keys: List<Any>,
                              val id: String,
                              val input_spec: InputSpec,
                              val is_nsfw: Boolean,
                              val photo_url_list: List<Any>,
                              val premium: Boolean,
                              val result: Any,
                              val state: String,
                              val updated_at: String,
                              val user_id: String)
