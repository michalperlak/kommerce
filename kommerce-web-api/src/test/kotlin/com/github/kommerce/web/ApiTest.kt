package com.github.kommerce.web

import org.junit.jupiter.api.extension.ExtendWith

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@ExtendWith(ServerExtension::class)
annotation class ApiTest