package com.github.kommerce.users

import arrow.core.getOrElse
import arrow.core.getOrHandle
import com.github.kommerce.users.dto.NewUserDto
import com.github.kommerce.users.dto.UserDto
import org.junit.jupiter.api.Assertions.assertEquals
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
        val email = "test@test.com"
        val usersModule = createUsersModule()
        val user = addUser(email, usersModule)

        // when
        val result = usersModule.getUser(user.id)

        // then
        val foundUser = result.getOrElse { throw AssertionError("User not found") }
        assertEquals(user, foundUser)
    }

    private fun addUser(email: String, usersModule: UsersModule): UserDto {
        val newUser = NewUserDto(email)
        return usersModule
            .addUser(newUser)
            .getOrHandle { throw IllegalStateException("Expected user but was: $it") }
    }
}