package com.example.afaloan.controller.microloans.dtos

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size
import java.math.BigDecimal

data class MicroloanDto(
    @field:Size(min = 1)
    val name: String,
    @field:Min(value = 1)
    val sum: BigDecimal,
    @field:Min(value = 1)
    val monthlyIncomeRequirement: BigDecimal,
    val otherRequirements: String? = null
)
