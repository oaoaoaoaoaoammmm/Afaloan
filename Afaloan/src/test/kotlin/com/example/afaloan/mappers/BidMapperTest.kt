package com.example.afaloan.mappers

import com.example.afaloan.controller.bids.dtos.CreateBidRequest
import com.example.afaloan.models.Bid
import com.example.afaloan.models.BoilingPoint
import com.example.afaloan.models.Microloan
import com.example.afaloan.models.Profile
import com.example.afaloan.models.enumerations.BidPriority
import com.example.afaloan.models.enumerations.BidStatus
import com.example.afaloan.services.BoilingPointService
import com.example.afaloan.services.MicroloanService
import com.example.afaloan.services.ProfileService
import com.example.afaloan.utils.USER
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

class BidMapperTest {

    private val profileService = mock<ProfileService>()
    private val microloanService = mock<MicroloanService>()
    private val boilingPointService = mock<BoilingPointService>()

    private val bidMapper = BidMapper(profileService, microloanService, boilingPointService)

    @Test
    fun `convertToDto should execute successfully`() {
        val bid = createBid()

        val result = bidMapper.convertToDto(bid)

        assertThat(bid.target).isEqualTo(result.target)
        assertThat(bid.status).isEqualTo(result.status)
        assertThat(bid.profile!!.id).isEqualTo(result.profileId)
        assertThat(bid.microloan!!.id).isEqualTo(result.microloanId)
        assertThat(bid.boilingPoint!!.id).isEqualTo(result.boilingPointId)
    }

    @Test
    fun `convertToView should execute successfully`() {
        val bid = createBid()

        val result = bidMapper.convertToView(bid)

        assertThat(bid.id).isEqualTo(result.id)
        assertThat(bid.target).isEqualTo(result.target)
        assertThat(bid.status).isEqualTo(result.status)
    }

    @Test
    fun `convert should execute successfully`() {
        val bid = createBid()
        val request = createBidRequest(bid)
        whenever(profileService.find(any())).thenReturn(bid.profile)
        whenever(microloanService.find(any())).thenReturn(bid.microloan)
        whenever(boilingPointService.find(any())).thenReturn(bid.boilingPoint)

        val result = bidMapper.convert(request)

        assertThat(bid.target).isEqualTo(result.target)
        assertThat(bid.coverLetter).isEqualTo(result.coverLetter)
        assertThat(bid.status).isEqualTo(result.status)
        assertThat(bid.employeeMessage).isEqualTo(result.employeeMessage)
        assertThat(result.profile).isNotNull
        assertThat(result.microloan).isNotNull
        assertThat(result.boilingPoint).isNotNull
    }

    private fun createBidRequest(bid: Bid): CreateBidRequest {
        return CreateBidRequest(
            target = bid.target,
            coverLetter = bid.coverLetter,
            priority = bid.priority,
            employeeMessage = bid.employeeMessage,
            profileId = bid.profile!!.id!!,
            microloanId = bid.microloan!!.id!!,
            boilingPointId = bid.boilingPoint!!.id!!
        )
    }

    private fun createBid(): Bid {
        return Bid(
            id = UUID.randomUUID(),
            target = "target",
            coverLetter = "cover letter",
            date = LocalDateTime.now(),
            priority = BidPriority.MEDIUM,
            status = BidStatus.UNDER_CONSIDERATION,
            employeeMessage = "employee message",
            profile = Profile(
                id = UUID.randomUUID(),
                name = "name",
                surname = "surname",
                patronymic = "patronymic",
                phoneNumber = "+79832422045",
                passportSeries = "1234",
                passportNumber = "123456",
                monthlyIncome = BigDecimal.TEN,
                user = USER
            ),
            microloan = Microloan(
                id = UUID.randomUUID(),
                name = "name",
                sum = BigDecimal.TEN,
                monthlyInterest = BigDecimal.ZERO,
                conditions = "conditions",
                monthlyIncomeRequirement = BigDecimal.TWO,
                otherRequirements = "other requirements"
            ),
            boilingPoint = BoilingPoint(
                id = UUID.randomUUID(),
                city = "Taishet",
                address = "Cherniy vanya 3a",
                openingHours = "Пн-Пт: 09:00-18:00, Сб: 10:00-14:00, Вс: выходной",
                info = "info"
            )
        )
    }
}