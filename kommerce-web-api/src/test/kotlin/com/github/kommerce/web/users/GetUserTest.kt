package com.github.kommerce.web.users

import com.github.kommerce.users.dto.UserDto
import com.github.kommerce.web.ApiTest
import com.github.kommerce.web.ServerExtension.Companion.TEST_PORT
import com.github.kommerce.web.extractBody
import com.github.kommerce.web.users.UsersHandler.Companion.BASE_USERS_PATH
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

@ApiTest
class GetUserTest {

    @Test
    fun `should return 200 and user data when user exists`() {
        val email = "test@example.com"
        val userId = addUser(email)
        val user = Given {
            port(TEST_PORT)
        } When {
            get("$BASE_USERS_PATH/$userId")
        } Then {
            statusCode(200)
        } Extract {
            extractBody<UserDto>()
        }
        assertAll(
            { assertEquals(userId, user.id) },
            { assertEquals(email, user.email) }
        )
    }

    @Test
    fun `should return 404 when user not found`() {
        Given {
            port(TEST_PORT)
        } When {
            get("$BASE_USERS_PATH/12345")
        } Then {
            statusCode(404)
        }
    }
}