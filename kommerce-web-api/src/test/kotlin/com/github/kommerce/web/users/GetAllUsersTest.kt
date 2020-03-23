package com.github.kommerce.web.users

import com.github.kommerce.users.dto.UserDto
import com.github.kommerce.web.ApiTest
import com.github.kommerce.web.ServerExtension.Companion.TEST_PORT
import com.github.kommerce.web.extractBody
import com.github.kommerce.web.users.UsersHandler.Companion.BASE_USERS_PATH
import com.squareup.moshi.Types
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

@ApiTest
internal class GetAllUsersTest {

    @Test
    fun `should return 200 and empty list when there are no users`() {
        val users = Given {
            port(TEST_PORT)
        } When {
            get(BASE_USERS_PATH)
        } Then {
            statusCode(200)
        } Extract {
            extractBody<List<UserDto>>()
        }
        assertEquals(emptyList<UserDto>(), users)
    }

    @Test
    fun `should return 200 and list of all existing users`() {
        val email1 = "test1@example.com"
        val user1 = addUser(email1)
        val email2 = "test2@example.com"
        val user2 = addUser(email2)
        val users: List<UserDto> =
            Given {
                port(TEST_PORT)
            } When {
                get(BASE_USERS_PATH)
            } Then {
                statusCode(200)
            } Extract {
                extractBody(Types.newParameterizedType(List::class.java, UserDto::class.java))
            }
        assertAll(
            { assertEquals(2, users.size) },
            { assertTrue(users.any { it.id == user1 }) },
            { assertTrue(users.any { it.email == email1 }) },
            { assertTrue(users.any { it.id == user2 }) },
            { assertTrue(users.any { it.email == email2 }) }
        )
    }
}