package com.example.afaloan.models

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.util.UUID

@Entity
@Table(name = "profiles")
data class Profile(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(name = "name")
    @field:Size(min = 1, max = 20)
    val name: String,

    @Column(name = "surname")
    @field:Size(min = 1, max = 20)
    val surname: String,

    @Column(name = "patronymic")
    @field:Size(max = 20)
    val patronymic: String? = null,

    @Column(name = "phone_number")
    @Pattern(regexp = "^\\+7\\d{10}$")
    val phoneNumber: String,

    @Column(name = "passport_series")
    @field:Size(min = 4, max = 4)
    val passportSeries: String,

    @Column(name = "passport_number")
    @field:Size(min = 6, max = 6)
    val passportNumber: String,

    @Column(name = "snils")
    @field:Size(min = 14, max = 14)
    val snils: String? = null,

    @Column(name = "inn")
    @field:Pattern(regexp = "^\\d{3}-\\d{3}-\\d{3}-\\d{2}$")
    val inn: String? = null,

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    val user: User
)
