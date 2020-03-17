package com.github.kommerce.users.domain

import arrow.core.Either
import com.github.kommerce.users.utils.of
import org.apache.commons.validator.routines.EmailValidator

data class Email internal constructor(
    private val email: String
) {
    override fun toString(): String = email

    companion object {
        fun parse(email: String): Either<Throwable, Email> =
            Either.of {
                val valid = EmailValidator
                    .getInstance()
                    .isValid(email)
                if (!valid) {
                    throw IllegalArgumentException("Email address: $email is not valid")
                }
                Email(email)
            }
    }
}
