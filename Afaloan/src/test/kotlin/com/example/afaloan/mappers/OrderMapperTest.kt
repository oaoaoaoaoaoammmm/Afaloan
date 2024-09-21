package com.example.afaloan.mappers

import com.example.afaloan.services.BoilingPointService
import com.example.afaloan.services.MicroloanService
import com.example.afaloan.services.ProfileService
import com.example.afaloan.utils.createOrder
import com.example.afaloan.utils.createCreateOrderRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class OrderMapperTest {

    private val profileService = mock<ProfileService>()
    private val microloanService = mock<MicroloanService>()
    private val boilingPointService = mock<BoilingPointService>()

    private val orderMapper = OrderMapper(profileService, microloanService, boilingPointService)

    @Test
    fun `convertToDto should execute successfully`() {
        val order = createOrder()

        val result = orderMapper.convertToDto(order)

        assertThat(order.target).isEqualTo(result.target)
        assertThat(order.status).isEqualTo(result.status)
        assertThat(order.profile!!.id).isEqualTo(result.profileId)
        assertThat(order.microloan!!.id).isEqualTo(result.microloanId)
        assertThat(order.boilingPoint!!.id).isEqualTo(result.boilingPointId)
    }

    @Test
    fun `convertToView should execute successfully`() {
        val order = createOrder()

        val result = orderMapper.convertToView(order)

        assertThat(order.id).isEqualTo(result.id)
        assertThat(order.target).isEqualTo(result.target)
        assertThat(order.status).isEqualTo(result.status)
    }

    @Test
    fun `convert should execute successfully`() {
        val order = createOrder()
        val request = createCreateOrderRequest(order)
        whenever(profileService.find(any())).thenReturn(order.profile)
        whenever(microloanService.find(any())).thenReturn(order.microloan)
        whenever(boilingPointService.find(any())).thenReturn(order.boilingPoint)

        val result = orderMapper.convert(request)

        assertThat(order.target).isEqualTo(result.target)
        assertThat(order.coverLetter).isEqualTo(result.coverLetter)
        assertThat(order.status).isEqualTo(result.status)
        assertThat(order.employeeMessage).isEqualTo(result.employeeMessage)
        assertThat(result.profile).isNotNull
        assertThat(result.microloan).isNotNull
        assertThat(result.boilingPoint).isNotNull
    }
}