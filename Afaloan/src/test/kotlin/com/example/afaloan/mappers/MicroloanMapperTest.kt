package com.example.afaloan.mappers

import com.example.afaloan.controller.microloans.dtos.MicroloanDto
import com.example.afaloan.models.Microloan
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*

class MicroloanMapperTest {

    private val microloanMapper = MicroloanMapper()

    @Test
    fun `convert(microloan Microloan) should execute successfully`() {
        val microloan = Microloan(
            id = UUID.randomUUID(),
            name = "name",
            sum = BigDecimal.TWO,
            monthlyInterest = BigDecimal.ZERO,
            conditions = "conditions",
            monthlyIncomeRequirement = BigDecimal.ONE,
            otherRequirements = "other requirements"
        )

        val result = microloanMapper.convert(microloan)

        assertThat(microloan.name).isEqualTo(result.name)
        assertThat(microloan.sum).isEqualTo(result.sum)
        assertThat(microloan.monthlyIncomeRequirement).isEqualTo(result.monthlyIncomeRequirement)
    }

    @Test
    fun `convert(dto MicroloanDto) should execute successfully`() {
        val dto = MicroloanDto(
            name = "name",
            sum = BigDecimal.TWO,
            monthlyInterest = BigDecimal.ZERO,
            conditions = "conditions",
            monthlyIncomeRequirement = BigDecimal.ONE,
            otherRequirements = "other requirements"
        )

        val result = microloanMapper.convert(dto)

        assertThat(dto.name).isEqualTo(result.name)
        assertThat(dto.sum).isEqualTo(result.sum)
        assertThat(dto.monthlyIncomeRequirement).isEqualTo(result.monthlyIncomeRequirement)
    }
}