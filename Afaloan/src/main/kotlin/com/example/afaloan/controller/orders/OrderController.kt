package com.example.afaloan.controller.orders

import com.example.afaloan.controller.orders.OrderController.Companion.ROOT_URI
import com.example.afaloan.controller.orders.dtos.OrderDto
import com.example.afaloan.controller.orders.dtos.OrderView
import com.example.afaloan.controller.orders.dtos.CreateOrderRequest
import com.example.afaloan.controller.orders.dtos.CreateOrderResponse
import com.example.afaloan.mappers.OrderMapper
import com.example.afaloan.models.enumerations.OrderStatus
import com.example.afaloan.services.OrderService
import com.example.afaloan.utils.logger
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping(ROOT_URI)
class OrderController(
    private val orderMapper: OrderMapper,
    private val orderService: OrderService
) {

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun find(@PathVariable id: UUID): OrderDto {
        logger.trace { "Find order by id - $id" }
        return orderService.find(id).let(orderMapper::convertToDto)
    }

    @GetMapping(params = ["boilingPointId"])
    @ResponseStatus(HttpStatus.OK)
    fun findPageByBoilingPointId(
        @RequestParam(required = false) boilingPointId: UUID,
        @Schema(hidden = true) @PageableDefault(size = DEFAULT_PAGE_SIZE) pageable: Pageable
    ): Page<OrderView> {
        logger.trace { "Find page of orders by boiling point id - $boilingPointId" }
        return orderService.findPageByBoilingPointId(boilingPointId, pageable).map(orderMapper::convertToView)
    }

    @GetMapping(params = ["profileId"])
    @ResponseStatus(HttpStatus.OK)
    fun findPageByProfileId(
        @RequestParam(required = false) profileId: UUID,
        @Schema(hidden = true) @PageableDefault(size = DEFAULT_PAGE_SIZE) pageable: Pageable
    ): Page<OrderView> {
        logger.trace { "Find page of orders by profile id - $profileId" }
        return orderService.findPageByProfileId(profileId, pageable).map(orderMapper::convertToView)
    }

    @GetMapping(params = ["microloanId"])
    @ResponseStatus(HttpStatus.OK)
    fun findPageByMicroloanId(
        @RequestParam(required = false) microloanId: UUID,
        @Schema(hidden = true) @PageableDefault(size = DEFAULT_PAGE_SIZE) pageable: Pageable
    ): Page<OrderView> {
        logger.trace { "Find page of orders by microloan id - $microloanId" }
        return orderService.findPageByMicroloanId(microloanId, pageable).map(orderMapper::convertToView)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid request: CreateOrderRequest): CreateOrderResponse {
        logger.trace { "Create order" }
        val order = orderMapper.convert(request)
        val id = orderService.create(order)
        return CreateOrderResponse(id)
    }

    @PatchMapping(value = ["/{id}"], params = ["status"])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateStatus(
        @PathVariable id: UUID,
        @RequestParam status: OrderStatus,
        @RequestParam(required = false) employeeMessage: String? = null
    ) {
        logger.trace { "Update status - $status for order with id - $id" }
        orderService.updateStatus(id, status, employeeMessage)
    }

    companion object {
        const val ROOT_URI = "\${api.prefix}/orders"
        const val DEFAULT_PAGE_SIZE = 50
    }
}