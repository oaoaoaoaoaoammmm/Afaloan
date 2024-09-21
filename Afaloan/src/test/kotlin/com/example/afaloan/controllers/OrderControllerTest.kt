package com.example.afaloan.controllers

import com.example.afaloan.BaseIntegrationTest
import com.example.afaloan.controller.orders.dtos.OrderDto
import com.example.afaloan.controller.orders.dtos.CreateOrderRequest
import com.example.afaloan.controller.orders.dtos.CreateOrderResponse
import com.example.afaloan.controller.boilingpoints.dtos.CreateBoilingPointResponse
import com.example.afaloan.controller.microloans.dtos.CreateMicroloanResponse
import com.example.afaloan.models.enumerations.OrderPriority
import com.example.afaloan.models.enumerations.OrderStatus
import com.example.afaloan.utils.createCreateBoilingPointRequest
import com.example.afaloan.utils.PROFILE
import com.example.afaloan.utils.createMicroloanDto
import com.example.afaloan.utils.toJson
import com.example.afaloan.utils.toObject
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.UUID

class OrderControllerTest: BaseIntegrationTest() {

    @Test
    fun `create should return CREATED`() {
        createOrder()
    }

    @Test
    fun `find should return OK`() {
        createOrderAndGet()
    }

    @Test
    fun `findPageByBoilingPointId should return OK`() {
        val order = createOrderAndGet()
        mockMvc.perform(
            get("$API_PREFIX/orders")
                .param("page", "0")
                .param("boilingPointId", order.boilingPointId.toString())
        ).andExpectAll(
            status().isOk,
            jsonPath("$.content[0].id").isNotEmpty,
            jsonPath("$.content[0].target").value(order.target),
            jsonPath("$.content[0].status").value(order.status.name)
        )
    }

    @Test
    fun `findPageByProfileId should return OK`() {
        val order = createOrderAndGet()
        mockMvc.perform(
            get("$API_PREFIX/orders")
                .param("page", "0")
                .param("profileId", order.profileId.toString())
        ).andExpectAll(
            status().isOk,
            jsonPath("$.content[0].id").isNotEmpty,
            jsonPath("$.content[0].target").value(order.target),
        )
    }

    @Test
    fun `findPageByMicroloanId should return OK`() {
        val order = createOrderAndGet()
        mockMvc.perform(
            get("$API_PREFIX/orders")
                .param("page", "0")
                .param("microloanId", order.microloanId.toString())
        ).andExpectAll(
            status().isOk,
            jsonPath("$.content[0].id").isNotEmpty,
            jsonPath("$.content[0].target").value(order.target),
            jsonPath("$.content[0].status").value(order.status.name)
        )
    }

    @Test
    fun `updateStatus should return NO_CONTENT`() {
        val orderId = createOrder()
        mockMvc.perform(
            patch("$API_PREFIX/orders/$orderId")
                .param("status", OrderStatus.REJECTED.name)
                .param("employeeMessage", "не не не... не не не.")
        ).andExpect(status().isNoContent)
    }

    private fun createOrderAndGet(): OrderDto {
        val orderId = createOrder()
        return mockMvc.perform(
            get("$API_PREFIX/orders/$orderId")
        ).andExpectAll(
            status().isOk,
            jsonPath("$.target").isNotEmpty,
            jsonPath("$.status").isNotEmpty,
            jsonPath("$.profileId").isNotEmpty,
        ).andReturn().response.contentAsString.toObject<OrderDto>()
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