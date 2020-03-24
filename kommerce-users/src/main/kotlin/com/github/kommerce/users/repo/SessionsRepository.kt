package com.github.kommerce.users.repo

import arrow.core.Option
import com.github.kommerce.users.domain.Session
import com.github.kommerce.users.domain.SessionId

interface SessionsRepository {
    fun add(session: Session): Session
    fun getSession(id: SessionId): Option<Session>
    fun deleteAll()
}