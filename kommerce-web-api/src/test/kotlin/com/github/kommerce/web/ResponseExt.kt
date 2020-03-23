package com.github.kommerce.web

import arrow.core.getOrHandle
import com.github.kommerce.web.json.JsonMapper
import com.github.kommerce.web.json.MoshiMapper
import com.github.kommerce.web.json.read
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response

inline fun <reified T : Any> ExtractableResponse<Response>.extractBody(): T {
    val content = body().asString()
    val mapper: JsonMapper = MoshiMapper()
    return mapper
        .read<T>(content)
        .getOrHandle { throw it }
}