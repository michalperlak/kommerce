package com.github.kommerce.common.domain

interface Identifiable<out ID : Any> {
    val id: ID
}