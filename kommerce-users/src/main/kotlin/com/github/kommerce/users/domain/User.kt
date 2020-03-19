package com.github.kommerce.users.domain

import com.github.kommerce.common.domain.Identifiable

data class User(
    override val id: UserId,
    val email: Email
) : Identifiable<UserId>