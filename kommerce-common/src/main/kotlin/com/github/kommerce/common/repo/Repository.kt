package com.github.kommerce.common.repo

import arrow.core.Option
import com.github.kommerce.common.domain.Identifiable

interface Repository<ID : Any, T : Identifiable<ID>> {
    fun getById(id: ID): Option<T>
    fun add(value: T): T
    fun <A : Any> findBy(value: A, extractor: (T) -> A): List<T>
    fun <A : Any> findOneBy(value: A, extractor: (T) -> A): Option<T>
}