package com.github.kommerce.users.repo

import arrow.core.Option
import com.github.kommerce.common.repo.InMemoryRepository
import com.github.kommerce.users.domain.Session
import com.github.kommerce.users.domain.SessionId

class InMemorySessionsRepo : SessionsRepository {
    private val repository = InMemoryRepository<SessionId, Session>()

    override fun add(session: Session): Session = repository.add(session)

    override fun getSession(id: SessionId): Option<Session> = repository.getById(id)

    override fun deleteAll() = repository.deleteAll()
}