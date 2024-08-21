package com.example.afaloan.mappers

import com.example.afaloan.controller.boilingpoints.dtos.CreateBoilingPointRequest
import com.example.afaloan.controller.boilingpoints.dtos.UpdateBoilingPointRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BoilingPointMapperTest {

    private val boilingPointMapper = BoilingPointMapper()

    @Test
    fun `convert(request CreateBoilingPointRequest) should execute successfully`() {
        val request = CreateBoilingPointRequest(
            city = "Taishet",
            address = "Cherniy vanya 3a",
            openingHours = "Пн-Пт: 09:00-18:00, Сб: 10:00-14:00, Вс: выходной",
            info = "info"
        )

        val result = boilingPointMapper.convert(request)

        assertThat(result.city).isEqualTo(request.city)
        assertThat(result.address).isEqualTo(request.address)
        assertThat(result.openingHours).isEqualTo(request.openingHours)
    }

    @Test
    fun `convert(request UpdateBoilingPointRequest) should execute successfully`() {
        val request = UpdateBoilingPointRequest(
            city = "Taishet",
            address = "Cherniy vanya 3a",
            openingHours = "Пн-Пт: 09:00-18:00, Сб: 10:00-14:00, Вс: выходной",
            info = "info"
        )

        val result = boilingPointMapper.convert(request)

        assertThat(result.city).isEqualTo(request.city)
        assertThat(result.address).isEqualTo(request.address)
        assertThat(result.openingHours).isEqualTo(request.openingHours)
    }
}