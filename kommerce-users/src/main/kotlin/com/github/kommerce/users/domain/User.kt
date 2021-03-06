package com.github.kommerce.users.domain

import com.github.kommerce.common.domain.Identifiable
import java.io.Serializable

data class User(
    override val id: UserId,
    val email: Email,
    val password: Password
) : Identifiable<UserId>, Serializable {

    companion object {
        private const val serialVersionUID: Long = 1
    }
}