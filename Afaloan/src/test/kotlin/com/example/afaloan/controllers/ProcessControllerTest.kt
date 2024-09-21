package com.example.afaloan.controllers

import com.example.afaloan.BaseIntegrationTest
import com.example.afaloan.controller.orders.dtos.CreateOrderRequest
import com.example.afaloan.controller.orders.dtos.CreateOrderResponse
import com.example.afaloan.controller.boilingpoints.dtos.CreateBoilingPointResponse
import com.example.afaloan.controller.microloans.dtos.CreateMicroloanResponse
import com.example.afaloan.controller.processes.dtos.CreateProcessRequest
import com.example.afaloan.controller.processes.dtos.CreateProcessResponse
import com.example.afaloan.controller.processes.dtos.ProcessDto
import com.example.afaloan.models.enumerations.OrderPriority
import com.example.afaloan.models.enumerations.ProcessStatus
import com.example.afaloan.utils.createCreateBoilingPointRequest
import com.example.afaloan.utils.PROFILE
import com.example.afaloan.utils.createMicroloanDto
import com.example.afaloan.utils.toJson
import com.example.afaloan.utils.toObject
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal
import java.util.*

class ProcessControllerTest : BaseIntegrationTest() {

    @Test
    fun `create should return CREATED`() {
        createProcess()
    }

    @Test
    fun `find should return OK`() {
        val processId = createProcess()
        mockMvc.perform(
            get("$API_PREFIX/processes/$processId")
        ).andExpectAll(
            status().isOk,
            jsonPath("$.debt").isNotEmpty,
            jsonPath("$.comment").isNotEmpty,
            jsonPath("$.status").isNotEmpty,
            jsonPath("$.orderId").isNotEmpty
        )
    }

    @Test
    fun `findPage should return OK`() {
        createProcess()
        mockMvc.perform(
            get("$API_PREFIX/processes")
                .param("page", "0")
        ).andExpectAll(
            status().isOk,
            jsonPath("$.content[0].id").isNotEmpty,
            jsonPath("$.content[0].debt").isNotEmpty,
            jsonPath("$.content[0].comment").isNotEmpty,
            jsonPath("$.content[0].status").isNotEmpty
        )
    }

    @Test
    fun `update should return OK`() {
        val processId = createProcess()
        mockMvc.perform(
            put("$API_PREFIX/processes/$processId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    ProcessDto(
                        debt = BigDecimal.ZERO,
                        comment = "comment",
                        status = ProcessStatus.CLOSED,
                        orderId = createOrder()
                    ).toJson()
                )
        ).andExpectAll(
            status().isOk,
            jsonPath("$.debt").value(BigDecimal.ZERO.toString()),
            jsonPath("$.comment").value("comment"),
            jsonPath("$.status").value(ProcessStatus.CLOSED.toString()),
            jsonPath("$.orderId").isNotEmpty
        )
    }

    private fun createProcess(): UUID {
        val response = mockMvc.perform(
            post("$API_PREFIX/processes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    CreateProcessRequest(
                        debt = BigDecimal.TEN,
                        comment = "comment",
                        orderId = createOrder()
                    ).toJson()
                )
        ).andExpectAll(
            status().isCreated,
            jsonPath("$.id").isNotEmpty
        ).andReturn().response.contentAsString.toObject<CreateProcessResponse>()
        return response.id
    }

    private fun createOrder(): UUID {
        val response = mockMvc.perform(
            post("$API_PREFIX/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    CreateOrderRequest(
                        target = "target",
                        coverLetter = "cover letter",
                        priority = OrderPriority.MEDIUM,
                        employeeMessage = "employee message",
                        profileId = createProfile(),
                        microloanId = createMicroloan(),
                        boilingPointId = createBoilingPoint()
                    ).toJson()
                )
        ).andExpectAll(
            status().isCreated,
            jsonPath("$.id").isNotEmpty
        ).andReturn().response.contentAsString.toObject<CreateOrderResponse>()
        return response.id
    }

    private fun createProfile(): UUID {
        return PROFILE.id!!
    }

    private fun createMicroloan(): UUID {
        val response = mockMvc.perform(
            post("$API_PREFIX/microloans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createMicroloanDto().toJson())
        ).andExpectAll(
            status().isCreated,
            jsonPath("$.id").isNotEmpty
        ).andReturn().response.contentAsString.toObject<CreateMicroloanResponse>()
        return response.id
    }

    private fun createBoilingPoint(): UUID {
        val response = mockMvc.perform(
            post("$API_PREFIX/boiling-points")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createCreateBoilingPointRequest().toJson())
        ).andExpectAll(
            status().isCreated,
            jsonPath("$.id").isNotEmpty
        ).andReturn().response.contentAsString.toObject<CreateBoilingPointResponse>()
        return response.id
    }
}