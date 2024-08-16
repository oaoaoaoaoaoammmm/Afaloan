package com.example.afaloan.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import jakarta.validation.constraints.Size
import java.util.UUID

@Entity
@Table(name = "users")
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(name = "username")
    @field:Size(min = 10, max = 64)
    val username: String,

    @Column(name = "password")
    @field:Size(min = 5, max = 60)
    val password: String,

    @Column(name = "confirmed")
    val confirmed: Boolean = false,

    @Column(name = "confirmed_username")
    val confirmedUsername: Boolean = false,

    @Column(name = "blocked")
    val blocked: Boolean = false,

    @field:Size(min = 1)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    val roles: Set<UserRole>
)
