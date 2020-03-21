package com.github.kommerce.users.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDto(
    val id: String,
    val email: String
)