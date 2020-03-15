package com.github.kommerce.users.utils

import arrow.core.Either
import arrow.core.nonFatalOrThrow

fun <T : Any> Either.Companion.of(supplier: () -> T): Either<Throwable, T> = try {
    right(supplier())
} catch (e: Throwable) {
    left(e.nonFatalOrThrow())
}