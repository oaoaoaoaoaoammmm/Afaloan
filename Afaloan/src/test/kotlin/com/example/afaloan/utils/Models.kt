package com.example.afaloan.utils

import com.example.afaloan.models.User
import com.example.afaloan.models.UserRole
import com.example.afaloan.models.enumerations.Role
import java.util.*

val ROLES = Role.entries.map { UserRole(role = it) }.toSet()

const val USER_PASSWORD = "12345"

const val ENCODED_USER_PASSWORD = "\$2a\$10\$.IEUyyxTZjIGYnDHOcFW3e8AD5QFAKWj7nu7NM1NfBs.wE6AtC83a"

var USER = User(
    id = UUID.randomUUID(),
    username = "john.doe@mail.ru",
    password = ENCODED_USER_PASSWORD,
    confirmed = true,
    confirmedUsername = true,
    blocked = false,
    roles = ROLES
)

val UNAUTHORIZED_USER = User(
    id = UUID.randomUUID(),
    username = "johan.do@mail.ru",
    password = "12345678",
    confirmed = false,
    confirmedUsername = false,
    blocked = false,
    roles = setOf(UserRole(id = UUID.randomUUID(), Role.CUSTOMER))
)