package com.example.afaloan.services

import com.example.afaloan.exceptions.ErrorCode
import com.example.afaloan.exceptions.InternalException
import com.example.afaloan.models.Order
import com.example.afaloan.models.enumerations.OrderStatus
import com.example.afaloan.repositories.OrderRepository
import com.example.afaloan.utils.createOrder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import java.util.*

class OrderServiceTest {

    private val orderRepository = mock<OrderRepository>()

    private val orderService = OrderService(orderRepository)

    @Test
    fun `find should execute successfully`() {
        val order = createOrder()
        whenever(orderRepository.findById(any())).thenReturn(Optional.of(order))

        val result = orderService.find(order.id!!)

        assertThat(order.id).isEqualTo(result.id)
        assertThat(order.target).isEqualTo(result.target)
        assertThat(order.status).isEqualTo(result.status)
    }

    @Test
    fun `find should throw ORDER_NOT_FOUND`() {
        whenever(orderRepository.findById(any())).thenReturn(Optional.empty())

        val ex = assertThrows<InternalException> { orderService.find(UUID.randomUUID()) }

        assertThat(ex.httpStatus).isEqualTo(HttpStatus.NOT_FOUND)
        assertThat(ex.errorCode).isEqualTo(ErrorCode.ORDER_NOT_FOUND)
    }

    @Test
    fun `findPageByBoilingPointId should execute successfully`() {
        val page = PageImpl(listOf(createOrder(), createOrder(), createOrder()))
        whenever(orderRepository.findPageByBoilingPointId(any(), any())).thenReturn(page)

        val result = orderService.findPageByBoilingPointId(UUID.randomUUID(), Pageable.ofSize(10))

        assertThat(page.size).isEqualTo(result.size)
    }

    @Test
    fun `findPageByProfileId should execute successfully`() {
        val page = PageImpl(listOf(createOrder(), createOrder(), createOrder()))
        whenever(orderRepository.findPageByProfileId(any(), any())).thenReturn(page)

        val result = orderService.findPageByProfileId(UUID.randomUUID(), Pageable.ofSize(10))

        assertThat(page.size).isEqualTo(result.size)
    }

    @Test
    fun `findPageByMicroloanId should execute successfully`() {
        val page = PageImpl(listOf(createOrder(), createOrder(), createOrder()))
        whenever(orderRepository.findPageByMicroloanId(any(), any())).thenReturn(page)

        val result = orderService.findPageByMicroloanId(UUID.randomUUID(), Pageable.ofSize(10))

        assertThat(page.size).isEqualTo(result.size)
    }

    @Test
    fun `create should execute successfully`() {
        val order = createOrder()
        whenever(orderRepository.save(any<Order>())).thenReturn(order)

        val result = orderService.create(order)

        assertThat(result).isNotNull()
    }

    @Test
    fun `updateStatus should execute successfully`() {
        val order = createOrder()
        whenever(orderRepository.findById(any())).thenReturn(Optional.of(order))

        assertDoesNotThrow { orderService.updateStatus(order.id!!, OrderStatus.CLOSED) }
    }
}