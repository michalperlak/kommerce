package com.github.kommerce.web.error

class ParseError(message: String) : RuntimeException(message) {
    override fun fillInStackTrace(): Throwable? = null
}