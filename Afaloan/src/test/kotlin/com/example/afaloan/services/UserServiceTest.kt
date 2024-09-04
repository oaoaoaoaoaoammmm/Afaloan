package com.example.afaloan.services

import com.example.afaloan.exceptions.ErrorCode
import com.example.afaloan.exceptions.InternalException
import com.example.afaloan.models.User
import com.example.afaloan.models.UserRole
import com.example.afaloan.repositories.UserRepository
import com.example.afaloan.utils.UNAUTHORIZED_USER
import com.example.afaloan.utils.USER
import com.example.afaloan.utils.mockSecurityContext
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

class UserServiceTest {

    private val roleService = mock<RoleService>()
    private val userRepository = mock<UserRepository>()

    private val userService = UserService(roleService, userRepository)

    @BeforeEach
    fun setUp() = mockSecurityContext()

    @AfterEach
    fun tearDown() = SecurityContextHolder.clearContext()

    @Test
    fun `isExists should return true`() {
        whenever(userRepository.existsByUsername(any())).thenReturn(true)

        val result = userService.isExists(USER.username)

        assertThat(result).isTrue()
    }

    @Test
    fun `find(id UUID) should execute successfully`() {
        whenever(userRepository.findById(any())).thenReturn(Optional.of(USER))

        val result = userService.find(USER.id!!)

        assertThat(result.id).isEqualTo(USER.id)
        assertThat(result.username).isEqualTo(USER.username)
    }

    @Test
    fun `find(id UUID) should throw USER_NOT_FOUND`() {
        whenever(userRepository.findById(any())).thenReturn(Optional.empty())

        val ex = assertThrows<InternalException> { userService.find(USER.id!!) }

        assertThat(ex.httpStatus).isEqualTo(HttpStatus.NOT_FOUND)
        assertThat(ex.errorCode).isEqualTo(ErrorCode.USER_NOT_FOUND)
    }

    @Test
    fun `find(username String) should execute successfully`() {
        whenever(userRepository.findByUsername(any())).thenReturn(USER)

        val result = userService.find(USER.username)

        assertThat(result.id).isEqualTo(USER.id)
        assertThat(result.username).isEqualTo(USER.username)
    }

    @Test
    fun `find(username String) should throw USER_NOT_FOUND`() {
        whenever(userRepository.findByUsername(any())).thenReturn(null)

        val ex = assertThrows<InternalException> { userService.find(USER.username) }

        assertThat(ex.httpStatus).isEqualTo(HttpStatus.NOT_FOUND)
        assertThat(ex.errorCode).isEqualTo(ErrorCode.USER_NOT_FOUND)
    }

    @Test
    fun `updateRoles should execute successfully`() {
        val roles = USER.roles
        whenever(userRepository.findById(any())).thenReturn(Optional.of(USER))
        whenever(roleService.isExists(roles.map(UserRole::role).toSet())).thenReturn(true)
        whenever(userRepository.save(any<User>())).thenReturn(USER.copy(roles = roles))

        val result = userService.updateRoles(USER.id!!, USER.roles)

        assertThat(result.id).isEqualTo(USER.id)
        assertThat(result.username).isEqualTo(USER.username)
        assertThat(result.roles).isEqualTo(USER.roles)
    }

    @Test
    fun `block should execute successfully`() {
        whenever(userRepository.findById(any())).thenReturn(Optional.of(USER))

        assertDoesNotThrow { userService.block(USER.id!!) }
    }

    @Test
    fun `unblock should execute successfully`() {
        whenever(userRepository.findById(any())).thenReturn(Optional.of(USER))

        assertDoesNotThrow { userService.unblock(USER.id!!) }
    }

    @Test
    fun `confirm should execute successfully`() {
        whenever(userRepository.findById(any())).thenReturn(Optional.of(USER))

        assertDoesNotThrow { userService.confirm(USER.id!!) }
    }

    @Test
    fun `delete(id UUID) should execute successfully`() {
        whenever(userRepository.findById(any())).thenReturn(Optional.of(USER))

        assertDoesNotThrow { userService.delete(USER.id!!) }
    }

    @Test
    fun `delete(id UUID) should throw FORBIDDEN`() {
        whenever(userRepository.findById(any())).thenReturn(Optional.of(UNAUTHORIZED_USER))

        val ex = assertThrows<InternalException> { userService.delete(UNAUTHORIZED_USER.id!!) }

        assertThat(ex.httpStatus).isEqualTo(HttpStatus.FORBIDDEN)
        assertThat(ex.errorCode).isEqualTo(ErrorCode.FORBIDDEN)
    }

    @Test
    fun `delete(username String) should execute successfully`() {
        whenever(userRepository.findByUsername(any())).thenReturn(USER)

        assertDoesNotThrow { userService.delete(USER.username) }
    }

    @Test
    fun `delete(username String) should throw FORBIDDEN`() {
        whenever(userRepository.findByUsername(any())).thenReturn(UNAUTHORIZED_USER)

        val ex = assertThrows<InternalException> { userService.delete(UNAUTHORIZED_USER.username) }

        assertThat(ex.httpStatus).isEqualTo(HttpStatus.FORBIDDEN)
        assertThat(ex.errorCode).isEqualTo(ErrorCode.FORBIDDEN)
    }

    /*

    @Test
    fun `updateUsername should execute successfully`() {
        val newUsername = "SO5O4KA@mail.ru"
        whenever(userRepository.findById(any())).thenReturn(Optional.of(USER))
        whenever(userRepository.save(any<User>())).thenReturn(USER.copy(username = newUsername))

        val result = userService.updateUsername(USER.id!!, newUsername)

        assertThat(result.id).isEqualTo(USER.id)
        assertThat(result.username).isEqualTo(newUsername)
    }

    @Test
    fun `updateUsername should throw FORBIDDEN`() {
        val newUsername = "SO5O4KA@mail.ru"
        whenever(userRepository.findById(any())).thenReturn(Optional.of(UNAUTHORIZED_USER))

        val ex = assertThrows<InternalException> { userService.updateUsername(UNAUTHORIZED_USER.id!!, newUsername) }

        assertThat(ex.httpStatus).isEqualTo(HttpStatus.FORBIDDEN)
        assertThat(ex.errorCode).isEqualTo(ErrorCode.FORBIDDEN)
    }

    @Test
    fun `updatePassword should execute successfully`() {
        val newPassword = "new_ppp"
        whenever(userRepository.findById(any())).thenReturn(Optional.of(USER))
        whenever(userRepository.save(any<User>())).thenReturn(USER.copy(password = newPassword))

        assertDoesNotThrow { userService.updatePassword(USER.id!!, USER.password, newPassword) }
    }

    @Test
    fun `updatePassword should throw FORBIDDEN`() {
        val newPassword = "new_ppp"
        whenever(userRepository.findById(any())).thenReturn(Optional.of(UNAUTHORIZED_USER))

        val ex = assertThrows<InternalException> {
            userService.updatePassword(UNAUTHORIZED_USER.id!!, UNAUTHORIZED_USER.password, newPassword)
        }

        assertThat(ex.httpStatus).isEqualTo(HttpStatus.FORBIDDEN)
        assertThat(ex.errorCode).isEqualTo(ErrorCode.FORBIDDEN)
    }

    @Test
    fun `updatePassword should throw USER_PASSWORD_INCORRECT`() {
        val newPassword = "new_ppp"
        whenever(userRepository.findById(any())).thenReturn(Optional.of(USER))

        val ex = assertThrows<InternalException> {
            userService.updatePassword(USER.id!!, "incorrect password", newPassword)
        }

        assertThat(ex.httpStatus).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(ex.errorCode).isEqualTo(ErrorCode.USER_PASSWORD_INCORRECT)
    }

     */
}