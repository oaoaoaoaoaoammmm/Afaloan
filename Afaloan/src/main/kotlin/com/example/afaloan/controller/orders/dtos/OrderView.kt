package com.example.afaloan.controller.orders.dtos

import com.example.afaloan.models.enumerations.OrderPriority
import com.example.afaloan.models.enumerations.OrderStatus
import java.time.LocalDateTime
import java.util.*

data class OrderView(
    val id: UUID? = null,
    val target: String,
    val date: LocalDateTime,
    val priority: OrderPriority,
    val status: OrderStatus,
)
