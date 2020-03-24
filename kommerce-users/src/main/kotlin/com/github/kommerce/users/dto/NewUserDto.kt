package com.github.kommerce.users.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewUserDto(
    val email: String,
    val password: String
)