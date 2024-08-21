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
import java.util.UUID

@Entity
@Table(name = "profiles")
data class Profile(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(name = "name")
    val name: String,

    @Column(name = "surname")
    val surname: String,

    @Column(name = "patronymic")
    val patronymic: String? = null,

    @Column(name = "phone_number")
    val phoneNumber: String,

    @Column(name = "passport_series")
    val passportSeries: String,

    @Column(name = "passport_number")
    val passportNumber: String,

    @Column(name = "snils")
    val snils: String? = null,

    @Column(name = "inn")
    val inn: String? = null,

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    val user: User
)
