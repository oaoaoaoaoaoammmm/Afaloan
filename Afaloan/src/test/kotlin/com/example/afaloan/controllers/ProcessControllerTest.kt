package com.example.afaloan.controllers

import com.example.afaloan.BaseIntegrationTest
import com.example.afaloan.controller.bids.dtos.CreateBidRequest
import com.example.afaloan.controller.bids.dtos.CreateBidResponse
import com.example.afaloan.controller.boilingpoints.dtos.CreateBoilingPointRequest
import com.example.afaloan.controller.boilingpoints.dtos.CreateBoilingPointResponse
import com.example.afaloan.controller.microloans.dtos.CreateMicroloanResponse
import com.example.afaloan.controller.microloans.dtos.MicroloanDto
import com.example.afaloan.controller.processes.dtos.CreateProcessRequest
import com.example.afaloan.controller.processes.dtos.CreateProcessResponse
import com.example.afaloan.controller.processes.dtos.ProcessDto
import com.example.afaloan.controller.profiles.dtos.CreateProfileRequest
import com.example.afaloan.controller.profiles.dtos.CreateProfileResponse
import com.example.afaloan.models.enumerations.BidPriority
import com.example.afaloan.models.enumerations.ProcessStatus
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
            jsonPath("$.bidId").isNotEmpty
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
                        bidId = createBid()
                    ).toJson()
                )
        ).andExpectAll(
            status().isOk,
            jsonPath("$.debt").value(BigDecimal.ZERO.toString()),
            jsonPath("$.comment").value("comment"),
            jsonPath("$.status").value(ProcessStatus.CLOSED.toString()),
            jsonPath("$.bidId").isNotEmpty
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
                        bidId = createBid()
                    ).toJson()
                )
        ).andExpectAll(
            status().isCreated,
            jsonPath("$.id").isNotEmpty
        ).andReturn().response.contentAsString.toObject<CreateProcessResponse>()
        return response.id
    }

    private fun createBid(): UUID {
        val response = mockMvc.perform(
            post("$API_PREFIX/bids")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    CreateBidRequest(
                        target = "target",
                        coverLetter = "cover letter",
                        priority = BidPriority.MEDIUM,
                        employeeMessage = "employee message",
                        profileId = createProfile(),
                        microloanId = createMicroloan(),
                        boilingPointId = createBoilingPoint()
                    ).toJson()
                )
        ).andExpectAll(
            status().isCreated,
            jsonPath("$.id").isNotEmpty
        ).andReturn().response.contentAsString.toObject<CreateBidResponse>()
        return response.id
    }

    private fun createProfile(): UUID {
        val response = mockMvc.perform(
            post("$API_PREFIX/profiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    CreateProfileRequest(
                        name = "name",
                        surname = "surname",
                        patronymic = "patronymic",
                        phoneNumber = "+79832422045",
                        passportSeries = "1234",
                        passportNumber = "123456",
                        monthlyIncome = BigDecimal.TEN
                    ).toJson()
                )
        ).andExpectAll(
            status().isCreated,
            jsonPath("$.id").isNotEmpty
        ).andReturn().response.contentAsString.toObject<CreateProfileResponse>()
        return response.id
    }

    private fun createMicroloan(): UUID {
        val response = mockMvc.perform(
            post("$API_PREFIX/microloans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    MicroloanDto(
                        name = "name",
                        sum = BigDecimal.ONE,
                        monthlyInterest = BigDecimal.ZERO,
                        conditions = "conditions",
                        monthlyIncomeRequirement = BigDecimal.TWO,
                        otherRequirements = "other requirements"
                    ).toJson()
                )
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
                .content(
                    CreateBoilingPointRequest(
                        city = "Taishet",
                        address = "Cherniy vanya 3a",
                        openingHours = "Пн-Пт: 09:00-18:00, Сб: 10:00-15:00, Вс: выходной",
                        info = "info"
                    ).toJson()
                )
        ).andExpectAll(
            status().isCreated,
            jsonPath("$.id").isNotEmpty
        ).andReturn().response.contentAsString.toObject<CreateBoilingPointResponse>()
        return response.id
    }
}