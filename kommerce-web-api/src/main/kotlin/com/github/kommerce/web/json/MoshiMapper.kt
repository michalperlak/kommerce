package com.github.kommerce.web.json

import arrow.core.Either
import com.github.kommerce.web.error.ParseError
import com.github.kommerce.users.utils.of
import com.squareup.moshi.Moshi
import com.squareup.moshi.Moshi.Builder

class MoshiMapper(
    private val moshi: Moshi = Builder().build()
) : JsonMapper {

    override fun <T : Any> read(value: String, targetClass: Class<T>): Either<Throwable, T> =
        Either.of {
            val adapter = moshi.adapter(targetClass)
            adapter
                .fromJson(value)
                ?: throw ParseError("Cannot extract ${targetClass.name} instance from value: $value")
        }

    override fun <T : Any> write(value: T): String {
        val adapter = moshi.adapter(value.javaClass)
        return adapter.toJson(value)
    }
}