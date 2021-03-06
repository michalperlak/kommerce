package com.github.kommerce.web.json

import arrow.core.Either
import com.github.kommerce.users.utils.of
import com.github.kommerce.web.error.ParseError
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Moshi.Builder
import java.lang.reflect.Type

class MoshiMapper(
    private val moshi: Moshi = Builder().build()
) : JsonMapper {

    override fun <T : Any> read(value: String, targetClass: Class<T>): Either<Throwable, T> =
        Either.of {
            val adapter = getAdapter(targetClass)
            adapter
                .fromJson(value)
                ?: throw ParseError("Cannot extract ${targetClass.name} instance from value: $value")
        }

    fun <T : Any> read(value: String, type: Type): Either<Throwable, T> =
        Either.of {
            val adapter = moshi.adapter<T>(type)
            adapter
                .fromJson(value)
                ?: throw ParseError("Cannot extract ${type.typeName} instance from value: $value")
        }

    override fun <T : Any> write(value: T): String {
        val adapter = getAdapter(value.javaClass)
        return adapter.toJson(value)
    }

    private fun <T : Any> getAdapter(type: Class<T>): JsonAdapter<T> =
        when {
            Collection::class.java.isAssignableFrom(type) -> moshi.adapter<T>(Object::class.java)
            Map::class.java.isAssignableFrom(type) -> moshi.adapter<T>(Object::class.java)
            else -> moshi.adapter(type)
        }
}