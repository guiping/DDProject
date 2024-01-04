package com.kiudysng.ddproject.entity

data class TokenEntity(    val expiresIn: String,
                           val idToken: String,
                           val kind: String,
                           val localId: String,
                           val refreshToken: String)
