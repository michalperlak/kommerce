package com.github.kommerce.users.domain

data class Email(
    private val email: String
) {
    override fun toString(): String = email
}
