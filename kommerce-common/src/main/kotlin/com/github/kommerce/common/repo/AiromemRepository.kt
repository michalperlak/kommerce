package com.github.kommerce.common.repo

import arrow.core.Option
import com.github.kommerce.common.domain.Identifiable
import pl.setblack.airomem.core.Persistent
import java.nio.file.Path

class AiromemRepository<ID : Any, T : Identifiable<ID>>(
    path: Path
) : Repository<ID, T>, AutoCloseable {
    private val persistent: Persistent<Repository<ID, T>> =
        Persistent.loadOptional(path) { InMemoryRepository() }

    override fun getById(id: ID): Option<T> =
        persistent.query { repo -> repo.getById(id) }

    override fun add(value: T): T =
        persistent.executeAndQuery { repo -> repo.add(value) }

    override fun <A : Any> findBy(value: A, extractor: (T) -> A): List<T> =
        persistent.query { repo -> repo.findBy(value, extractor) }

    override fun <A : Any> findOneBy(value: A, extractor: (T) -> A): Option<T> =
        persistent.query { repo -> repo.findOneBy(value, extractor) }

    override fun close() = persistent.close()
}