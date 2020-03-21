package com.github.kommerce.common.repo

import arrow.core.Option
import com.github.kommerce.common.domain.Identifiable
import pl.setblack.airomem.core.Persistent
import java.io.Serializable
import java.nio.file.Path

class AiromemRepository<ID : Serializable, T>(
    path: Path
) : Repository<ID, T> where T : Identifiable<ID>, T : Serializable {
    private val persistent = Persistent.loadOptional(path) { InMemoryRepository<ID, T>() }

    init {
        Runtime
            .getRuntime()
            .addShutdownHook(Thread { persistent.close() })
    }

    override fun getById(id: ID): Option<T> =
        persistent.query { repo -> repo.getById(id) }

    override fun add(value: T): T =
        persistent.executeAndQuery { repo -> repo.add(value) }

    override fun getAll(): List<T> =
        persistent.query { repo -> repo.getAll() }

    override fun <A : Any> findBy(value: A, extractor: (T) -> A): List<T> =
        persistent.query { repo -> repo.findBy(value, extractor) }

    override fun <A : Any> findOneBy(value: A, extractor: (T) -> A): Option<T> =
        persistent.query { repo -> repo.findOneBy(value, extractor) }
}