package com.github.kommerce.web.users

import com.github.kommerce.web.ApiTest
import com.github.kommerce.web.ServerExtension.Companion.TEST_PORT
import com.github.kommerce.web.users.UsersHandler.Companion.BASE_USERS_PATH
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.CoreMatchers.containsStringIgnoringCase
import org.hamcrest.CoreMatchers.startsWith
import org.junit.jupiter.api.Test

@ApiTest
internal class AddUserTest {

    @Test
    fun `should return 201 after successful user creation`() {
        Given {
            port(TEST_PORT)
            body(
                """
                    { "email": "test@example.com", "password": "abcd" }
                """.trimIndent()
            )
        } When {
            post(BASE_USERS_PATH)
        } Then {
            statusCode(201)
            header("location", startsWith(BASE_USERS_PATH))
        }
    }

    @Test
    fun `should return 400 when email address is not valid`() {
        Given {
            port(TEST_PORT)
            body(
                """
                    { "email": "test", "password": "abc" }
                """.trimIndent()
            )
        } When {
            post(BASE_USERS_PATH)
        } Then {
            statusCode(400)
            body(containsStringIgnoringCase("EMAIL_NOT_VALID"))
        }
    }

    @Test
    fun `should return 400 when email address already used`() {
        Given {
            port(TEST_PORT)
            addUser("test@example.com")
            body(
                """
                    { "email": "test@example.com", "password": "abc" }
                """.trimIndent()
            )
        } When {
            post(BASE_USERS_PATH)
        } Then {
            statusCode(400)
            body(containsStringIgnoringCase("EMAIL_ALREADY_USED"))
        }
    }
}