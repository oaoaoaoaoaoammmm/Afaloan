package com.example.afaloan.controller.orders.dtos

import com.example.afaloan.models.enumerations.OrderPriority
import jakarta.validation.constraints.Size
import java.util.*

data class CreateOrderRequest(
    @field:Size(min = 1, max = 64)
    val target: String,
    @field:Size(max = 500)
    val coverLetter: String? = null,
    val priority: OrderPriority,
    @field:Size(max = 64)
    val employeeMessage: String? = null,
    val profileId: UUID,
    val microloanId: UUID,
    val boilingPointId: UUID,
)
