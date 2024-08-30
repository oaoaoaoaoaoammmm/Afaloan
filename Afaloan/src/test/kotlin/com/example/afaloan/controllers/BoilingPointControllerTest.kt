package com.example.afaloan.controllers

import com.example.afaloan.BaseIntegrationTest
import com.example.afaloan.controller.boilingpoints.dtos.CreateBoilingPointRequest
import com.example.afaloan.controller.boilingpoints.dtos.CreateBoilingPointResponse
import com.example.afaloan.controller.boilingpoints.dtos.UpdateBoilingPointRequest
import com.example.afaloan.utils.toJson
import com.example.afaloan.utils.toObject
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

class BoilingPointControllerTest : BaseIntegrationTest() {

    @Test
    fun `create should return CREATED`() {
        createBoilingPoint()
    }

    @Test
    fun `find should return OK`() {
        val boilingPointId = createBoilingPoint()
        mockMvc.perform(
            get("$API_PREFIX/boiling-points/$boilingPointId")
        ).andExpectAll(
            status().isOk,
            jsonPath("$.id").isNotEmpty,
            jsonPath("$.city").isNotEmpty,
            jsonPath("$.openingHours").isNotEmpty
        )
    }

    @Test
    fun `update should return OK`() {
        val boilingPointId = createBoilingPoint()
        mockMvc.perform(
            put("$API_PREFIX/boiling-points/$boilingPointId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    UpdateBoilingPointRequest(
                        city = "Taishet",
                        address = "Cherniy vanya 3a",
                        openingHours = "Пн-Пт: 09:00-18:00, Сб: 10:00-14:00, Вс: выходной",
                        info = "info"
                    ).toJson()
                )
        ).andExpectAll(
            status().isOk,
            jsonPath("$.id").isNotEmpty,
            jsonPath("$.city").isNotEmpty,
            jsonPath("$.openingHours").isNotEmpty
        )
    }

    @Test
    fun `delete should return OK`() {
        val boilingPointId = createBoilingPoint()
        mockMvc.perform(
            delete("$API_PREFIX/boiling-points/$boilingPointId")
        ).andExpect(status().isNoContent)
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