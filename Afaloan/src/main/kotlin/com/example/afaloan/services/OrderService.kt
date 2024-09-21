package com.example.afaloan.services

import com.example.afaloan.exceptions.ErrorCode
import com.example.afaloan.exceptions.InternalException
import com.example.afaloan.models.Order
import com.example.afaloan.models.enumerations.OrderStatus
import com.example.afaloan.repositories.OrderRepository
import com.example.afaloan.utils.logger
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class OrderService(
    private val orderRepository: OrderRepository
) {

    fun find(id: UUID): Order {
        logger.info { "Finding order by id - $id" }
        return orderRepository.findById(id).orElseThrow {
            InternalException(httpStatus = HttpStatus.NOT_FOUND, errorCode = ErrorCode.ORDER_NOT_FOUND)
        }
    }

    fun findPageByBoilingPointId(boilingPointId: UUID, pageable: Pageable): Page<Order> {
        logger.info { "Finding orders page with number - ${pageable.pageNumber} by boiling point id - $boilingPointId" }
        return orderRepository.findPageByBoilingPointId(boilingPointId, pageable)
    }

    fun findPageByProfileId(profileId: UUID, pageable: Pageable): Page<Order> {
        logger.info { "Finding orders page with number - ${pageable.pageNumber} by profile id - $profileId" }
        val result = orderRepository.findPageByProfileId(profileId, pageable)
        return result
    }

    fun findPageByMicroloanId(microloanID: UUID, pageable: Pageable): Page<Order> {
        logger.info { "Finding orders page with number - ${pageable.pageNumber} by microloan id - $microloanID" }
        return orderRepository.findPageByMicroloanId(microloanID, pageable)
    }

    fun create(order: Order): UUID {
        logger.info { "Creating order" }
        return orderRepository.save(order).id!!
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    fun updateStatus(id: UUID, status: OrderStatus, employeeMessage: String? = null) {
        logger.info { "Updating status - $status for order with id - $id" }
        val order = find(id)
        val closedOrder = order.copy(status = status, employeeMessage = employeeMessage)
        orderRepository.save(closedOrder)
    }
}