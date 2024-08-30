package com.example.afaloan.mappers

import com.example.afaloan.controller.microloans.dtos.MicroloanDto
import com.example.afaloan.models.Microloan
import org.springframework.stereotype.Component

@Component
class MicroloanMapper {

    fun convert(microloan: Microloan): MicroloanDto {
        return MicroloanDto(
            name = microloan.name,
            sum = microloan.sum,
            monthlyIncomeRequirement = microloan.monthlyIncomeRequirement,
            otherRequirements = microloan.otherRequirements
        )
    }

    fun convert(dto: MicroloanDto): Microloan {
        return Microloan(
            id = null,
            name = dto.name,
            sum = dto.sum,
            monthlyIncomeRequirement = dto.monthlyIncomeRequirement,
            otherRequirements = dto.otherRequirements
        )
    }
}