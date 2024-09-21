package com.example.afaloan.mappers

import com.example.afaloan.controller.orders.dtos.OrderDto
import com.example.afaloan.controller.orders.dtos.OrderView
import com.example.afaloan.controller.orders.dtos.CreateOrderRequest
import com.example.afaloan.models.Order
import com.example.afaloan.models.enumerations.OrderStatus
import com.example.afaloan.services.BoilingPointService
import com.example.afaloan.services.MicroloanService
import com.example.afaloan.services.ProfileService
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class OrderMapper(
    private val profileService: ProfileService,
    private val microloanService: MicroloanService,
    private val boilingPointService: BoilingPointService
) {

    fun convertToDto(order: Order): OrderDto {
        return OrderDto(
            target = order.target,
            coverLetter = order.coverLetter,
            date = order.date,
            priority = order.priority,
            status = order.status,
            employeeMessage = order.employeeMessage,
            profileId = order.profile!!.id!!,
            microloanId = order.microloan!!.id!!,
            boilingPointId = order.boilingPoint!!.id!!
        )
    }

    fun convertToView(order: Order): OrderView {
        return OrderView(
            id = order.id,
            target = order.target,
            date = order.date,
            priority = order.priority,
            status = order.status
        )
    }

    fun convert(request: CreateOrderRequest): Order {
        return Order(
            id = null,
            target = request.target,
            coverLetter = request.coverLetter,
            date = LocalDateTime.now(),
            priority = request.priority,
            status = OrderStatus.UNDER_CONSIDERATION,
            employeeMessage = request.employeeMessage,
            profile = profileService.find(request.profileId),
            microloan = microloanService.find(request.microloanId),
            boilingPoint = boilingPointService.find(request.boilingPointId)
        )
    }
}