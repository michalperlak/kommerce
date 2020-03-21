package com.github.kommerce.web.error

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorMessage(val error: String)