package com.example.afaloan.controller.orders.dtos

import com.example.afaloan.models.enumerations.OrderPriority
import com.example.afaloan.models.enumerations.OrderStatus
import java.time.LocalDateTime
import java.util.UUID

data class OrderDto(
    val target: String,
    val coverLetter: String? = null,
    val date: LocalDateTime,
    val priority: OrderPriority,
    val status: OrderStatus,
    val employeeMessage: String? = null,
    val profileId: UUID? = null,
    val microloanId: UUID? = null,
    val boilingPointId: UUID? = null
)
