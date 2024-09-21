package com.example.afaloan.mappers

import com.example.afaloan.controller.processes.dtos.CreateProcessRequest
import com.example.afaloan.controller.processes.dtos.ProcessDto
import com.example.afaloan.controller.processes.dtos.ProcessView
import com.example.afaloan.models.Process
import com.example.afaloan.models.enumerations.ProcessStatus
import com.example.afaloan.services.OrderService
import org.springframework.stereotype.Component

@Component
class ProcessMapper(
    private val orderService: OrderService
) {

    fun convertToDto(process: Process): ProcessDto {
        return ProcessDto(
            debt = process.debt,
            status = process.status,
            comment = process.comment,
            orderId = process.order!!.id!!
        )
    }

    fun convertToView(process: Process): ProcessView {
        return ProcessView(
            id = process.id!!,
            debt = process.debt,
            status = process.status,
            comment = process.comment,
        )
    }

    fun convert(request: CreateProcessRequest): Process {
        return Process(
            debt = request.debt,
            status = ProcessStatus.CREATED,
            comment = request.comment,
            order = orderService.find(request.orderId)
        )
    }

    fun convert(dto: ProcessDto): Process {
        return Process(
            debt = dto.debt,
            comment = dto.comment,
            status = dto.status,
            order = orderService.find(dto.orderId)
        )
    }
}