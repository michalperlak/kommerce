package com.github.kommerce.users

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class LoginTest {

    @Test
    fun `should not create session when user does not exist`() {
        // given
        val usersModule = createUsersModule()

        // when
        val result = usersModule.login("test@example.com", "abc")

        // then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `should not create session when password is invalid`() {
        // given
        val usersModule = createUsersModule()
        val email = "test@example.com"
        addUser(email, "abcd123", usersModule)

        // when
        val result = usersModule.login(email, "abc")

        // then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `should return session data when password is valid`() {
        // given
        val usersModule = createUsersModule()
        val email = "test@example.com"
        val password = "abcd12345"
        addUser(email, password, usersModule)

        // when
        val result = usersModule.login(email, password)

        // then
        assertFalse(result.isEmpty())
    }
}