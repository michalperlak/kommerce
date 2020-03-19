package com.github.kommerce.common.repo

import arrow.core.Option
import com.github.kommerce.common.domain.Identifiable
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class InMemoryRepository<ID : Any, T : Identifiable<ID>> : Repository<ID, T> {
    private val values: ConcurrentMap<ID, T> = ConcurrentHashMap()

    override fun getById(id: ID): Option<T> = Option.fromNullable(values[id])

    override fun add(value: T): T = value.apply { values[id] = this }
}