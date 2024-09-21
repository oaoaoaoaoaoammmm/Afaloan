package com.example.afaloan.mappers

import com.example.afaloan.controller.users.dtos.CreateUserRequest
import com.example.afaloan.controller.users.dtos.UserDto
import com.example.afaloan.models.User
import com.example.afaloan.models.UserRole
import org.springframework.stereotype.Component

@Component
class UserMapper {

    fun convert(user: User): UserDto {
        return UserDto(
            id = user.id!!,
            username = user.username,
            confirmed = user.confirmed,
            confirmedUsername = user.confirmedUsername,
            blocked = user.blocked,
            roles = user.roles.map(UserRole::role)
        )
    }

    fun convert(createUserRequest: CreateUserRequest): User {
        return User(
            username = createUserRequest.username,
            password = createUserRequest.password,
            confirmed = false,
            confirmedUsername = false,
            blocked = false,
            roles = setOf()
        )
    }
}