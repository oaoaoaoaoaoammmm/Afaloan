package com.example.afaloan.controller.profiles.dtos

import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class UpdateProfileRequest(
    @field:Size(min = 1, max = 20)
    val name: String,
    @field:Size(min = 1, max = 20)
    val surname: String,
    @field:Size(max = 20)
    val patronymic: String? = null,
    @Pattern(regexp = "^\\+7\\d{10}$")
    val phoneNumber: String,
    @field:Size(min = 4, max = 4)
    val passportSeries: String,
    @field:Size(min = 6, max = 6)
    val passportNumber: String
)