package com.github.kommerce.web.users

import arrow.core.Option
import arrow.core.getOrElse
import arrow.core.getOrHandle
import com.github.kommerce.users.UsersModule
import com.github.kommerce.users.dto.LoginDto
import com.github.kommerce.web.ApiHandler
import com.github.kommerce.web.error.ErrorMessage
import com.github.kommerce.web.json.JsonMapper
import com.github.kommerce.web.json.read
import com.github.kommerce.web.util.ResponseSpec
import com.github.kommerce.web.util.bodyToMono
import com.github.kommerce.web.util.jsonContentType
import io.netty.handler.codec.http.HttpResponseStatus
import reactor.core.publisher.Mono
import reactor.netty.http.server.HttpServerRequest
import reactor.netty.http.server.HttpServerResponse
import reactor.netty.http.server.HttpServerRoutes

class SessionsHandler(
    private val usersModule: UsersModule,
    private val mapper: JsonMapper
) : ApiHandler {
    override fun invoke(routes: HttpServerRoutes): HttpServerRoutes =
        routes.post(SESSIONS_PATH, this::createSession)

    private fun createSession(request: HttpServerRequest, response: HttpServerResponse): Mono<Void> =
        request
            .bodyToMono { mapper.read<LoginDto>(it) }
            .flatMap {
                it
                    .map { loginData -> createSession(loginData) }
                    .getOrHandle { error -> parseError(error) }
            }
            .flatMap { it.applyAndSend(response) }

    private fun createSession(loginData: LoginDto): Mono<ResponseSpec> =
        Mono.just(
            usersModule
                .login(loginData.email, loginData.password)
                .map {
                    ResponseSpec(
                        status = HttpResponseStatus.OK,
                        headers = jsonContentType(),
                        body = Option.just(mapper.write(it))
                    )
                }
                .getOrElse {
                    ResponseSpec(
                        status = HttpResponseStatus.UNAUTHORIZED,
                        headers = jsonContentType()
                    )
                }
        )

    private fun parseError(throwable: Throwable): Mono<ResponseSpec> =
        Mono.just(
            ResponseSpec(
                status = HttpResponseStatus.BAD_REQUEST,
                headers = jsonContentType(),
                body = Option.just(
                    error("Error parsing login request: ${throwable.message ?: "UNKNOWN_ERROR"}")
                )
            )
        )

    private fun error(message: String): String =
        mapper.write(ErrorMessage(message))

    companion object {
        const val SESSIONS_PATH = "/api/users"
    }
}