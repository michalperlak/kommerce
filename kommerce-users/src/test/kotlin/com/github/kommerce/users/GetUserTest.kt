package com.github.kommerce.users

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.*

internal class GetUserTest {

    @Test
    fun `should return empty result when id is not valid`() {
        // given
        val userId = "abc123"
        val usersModule = createUsersModule()

        // when
        val result = usersModule.getUser(userId)

        // then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `should return empty result when user with id not found`() {
        // given
        val userId = UUID.randomUUID().toString()
        val usersModule = createUsersModule()

        // when
        val result = usersModule.getUser(userId)

        // then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `should return user data when user with id found`() {
        // given

    }
}