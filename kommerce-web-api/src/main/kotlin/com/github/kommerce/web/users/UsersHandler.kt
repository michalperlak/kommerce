package com.github.kommerce.web.users

import arrow.core.Option
import arrow.core.getOrElse
import arrow.core.getOrHandle
import com.github.kommerce.users.UsersModule
import com.github.kommerce.users.dto.NewUserDto
import com.github.kommerce.web.ApiHandler
import com.github.kommerce.web.error.ErrorMessage
import com.github.kommerce.web.json.JsonMapper
import com.github.kommerce.web.json.read
import com.github.kommerce.web.util.ResponseSpec
import com.github.kommerce.web.util.bodyToMono
import com.github.kommerce.web.util.jsonContentType
import com.github.kommerce.web.util.location
import io.netty.handler.codec.http.HttpResponseStatus
import reactor.core.publisher.Mono
import reactor.netty.http.server.HttpServerRequest
import reactor.netty.http.server.HttpServerResponse
import reactor.netty.http.server.HttpServerRoutes

class UsersHandler(
    private val usersModule: UsersModule,
    private val mapper: JsonMapper
) : ApiHandler {

    override fun invoke(routes: HttpServerRoutes): HttpServerRoutes =
        routes
            .post(BASE_USERS_PATH, this::createUser)
            .get(BASE_USERS_PATH) { _, response -> getAllUsers(response) }
            .get(GET_USER_PATH, this::getUser)

    private fun getAllUsers(response: HttpServerResponse): Mono<Void> =
        Mono
            .just(usersModule.getAllUsers())
            .map {
                ResponseSpec(
                    status = HttpResponseStatus.OK,
                    headers = jsonContentType(),
                    body = Option.just(mapper.write(it))
                )
            }
            .flatMap { it.applyAndSend(response) }

    private fun getUser(request: HttpServerRequest, response: HttpServerResponse): Mono<Void> =
        Mono
            .just(
                Option
                    .fromNullable(request.param("userId"))
                    .flatMap { usersModule.getUser(it) }
                    .map {
                        ResponseSpec(
                            status = HttpResponseStatus.OK,
                            headers = jsonContentType(),
                            body = Option.just(mapper.write(it))
                        )
                    }
                    .getOrElse {
                        ResponseSpec(
                            status = HttpResponseStatus.NOT_FOUND
                        )
                    }
            )
            .flatMap { it.applyAndSend(response) }

    private fun createUser(request: HttpServerRequest, response: HttpServerResponse): Mono<Void> =
        request
            .bodyToMono { mapper.read<NewUserDto>(it) }
            .flatMap {
                it
                    .map { newUser -> createUser(newUser) }
                    .getOrHandle { error -> parseError(error) }
            }
            .flatMap { it.applyAndSend(response) }

    private fun createUser(newUser: NewUserDto): Mono<ResponseSpec> =
        Mono.just(
            usersModule
                .addUser(newUser)
                .map {
                    ResponseSpec(
                        status = HttpResponseStatus.CREATED,
                        headers = jsonContentType().location("$BASE_USERS_PATH/${it.id}")
                    )
                }
                .getOrHandle {
                    ResponseSpec(
                        status = HttpResponseStatus.BAD_REQUEST,
                        headers = jsonContentType(),
                        body = Option.just(error(it.name))
                    )
                }
        )

    private fun parseError(throwable: Throwable): Mono<ResponseSpec> =
        Mono.just(
            ResponseSpec(
                status = HttpResponseStatus.BAD_REQUEST,
                headers = jsonContentType(),
                body = Option.just(
                    error("Error parsing creation request: ${throwable.message ?: "UNKNOWN_ERROR"}")
                )
            )
        )

    private fun error(message: String): String =
        mapper.write(ErrorMessage(message))

    companion object {
        const val BASE_USERS_PATH = "/api/users"
        const val GET_USER_PATH = "$BASE_USERS_PATH/{userId}"
    }
}