package com.github.kommerce.users

import arrow.core.Either
import com.github.kommerce.users.dto.NewUserDto
import com.github.kommerce.users.dto.UserDto
import com.github.kommerce.users.error.UserCreationError
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class AddUserTest {

    @Test
    fun `should return user data when user successfully added`() {
        // given
        val email = "test@test.com"
        val password = "1234"
        val userToAdd = NewUserDto(email, password)
        val usersModule = createUsersModule()

        // when
        val result = usersModule.addUser(userToAdd)

        // then
        assertSuccess(result) {
            assertEquals(email, it.email)
        }
    }

    @Test
    fun `should return error when email address not valid`() {
        // given
        val invalidEmail = "test"
        val password = "1234"
        val userToAdd = NewUserDto(invalidEmail, password)
        val usersModule = createUsersModule()

        // when
        val result = usersModule.addUser(userToAdd)

        // then
        assertError(UserCreationError.EMAIL_NOT_VALID, result)
    }

    @Test
    fun `should return error when email address already used`() {
        // given
        val email = "test@test.com"
        val password = "1234"
        val userToAdd = NewUserDto(email, password)
        val usersModule = createUsersModule()
        usersModule.addUser(userToAdd)

        // when
        val result = usersModule.addUser(userToAdd)

        // then
        assertError(UserCreationError.EMAIL_ALREADY_USED, result)
    }

    private fun assertError(expected: UserCreationError, result: Either<UserCreationError, UserDto>) {
        when (result) {
            is Either.Left -> assertEquals(expected, result.a)
            is Either.Right -> throw AssertionError("Expected error, but was: ${result.b}")
        }
    }

    private fun assertSuccess(result: Either<UserCreationError, UserDto>, assertions: (UserDto) -> Unit) {
        when (result) {
            is Either.Left -> throw AssertionError("Expected result, but was error: ${result.a}")
            is Either.Right -> assertions(result.b)
        }
    }
}