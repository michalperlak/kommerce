package com.github.kommerce.web

import arrow.core.getOrHandle
import com.github.kommerce.web.json.MoshiMapper
import com.github.kommerce.web.json.read
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import java.lang.reflect.Type

inline fun <reified T : Any> ExtractableResponse<Response>.extractBody(): T {
    val content = body().asString()
    val mapper = MoshiMapper()
    return mapper
        .read<T>(content)
        .getOrHandle { throw it }
}

fun <T : Any> ExtractableResponse<Response>.extractBody(type: Type): T {
    val content = body().asString()
    val mapper = MoshiMapper()
    return mapper
        .read<T>(content, type)
        .getOrHandle { throw it }
}