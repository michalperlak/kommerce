package com.github.kommerce.common.repo

import arrow.core.Option
import com.github.kommerce.common.domain.Identifiable
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class InMemoryRepository<ID : Any, T : Identifiable<ID>> : Repository<ID, T> {
    private val entries: ConcurrentMap<ID, T> = ConcurrentHashMap()

    override fun getById(id: ID): Option<T> = Option.fromNullable(entries[id])

    override fun add(value: T): T = value.apply { entries[id] = this }

    override fun <A : Any> findBy(value: A, extractor: (T) -> A): List<T> =
        entries
            .values
            .filter { value == extractor(it) }

    override fun <A : Any> findOneBy(value: A, extractor: (T) -> A): Option<T> =
        Option.fromNullable(
            entries
                .values
                .firstOrNull { value == extractor(it) }
        )
}