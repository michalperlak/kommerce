package com.github.kommerce.users

import com.github.kommerce.users.dto.UserDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GetAllUsersTest {

    @Test
    fun `should return empty list when there are no users`() {
        // given
        val usersModule = createUsersModule()

        // when
        val result = usersModule.getAllUsers()

        // then
        assertEquals(emptyList<UserDto>(), result)
    }

    @Test
    fun `should return all added users`() {
        // given
        val email = "test@example.com"
        val usersModule = createUsersModule()
        val user = addUser(email, "", usersModule)

        // when
        val result = usersModule.getAllUsers()

        // then
        assertEquals(listOf(user), result)
    }
}