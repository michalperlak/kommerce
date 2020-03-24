package com.github.kommerce.users.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SessionDto(
    val token: String,
    val expires: Long
)