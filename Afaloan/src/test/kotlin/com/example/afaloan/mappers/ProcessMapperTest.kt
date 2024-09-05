package com.example.afaloan.mappers

import com.example.afaloan.controller.processes.dtos.CreateProcessRequest
import com.example.afaloan.controller.processes.dtos.ProcessDto
import com.example.afaloan.models.Bid
import com.example.afaloan.models.Process
import com.example.afaloan.models.enumerations.BidPriority
import com.example.afaloan.models.enumerations.BidStatus
import com.example.afaloan.models.enumerations.ProcessStatus
import com.example.afaloan.services.BidService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

class ProcessMapperTest {

    private val bidService = mock<BidService>()

    private val processMapper = ProcessMapper(bidService)

    @Test
    fun `convertToDto should execute successfully`() {
        val process = createProcess()

        val result = processMapper.convertToDto(process)

        assertThat(process.debt).isEqualTo(result.debt)
        assertThat(process.comment).isEqualTo(result.comment)
        assertThat(process.status).isEqualTo(result.status)
    }

    @Test
    fun `convertToView should execute successfully`() {
        val process = createProcess()

        val result = processMapper.convertToView(process)

        assertThat(process.debt).isEqualTo(result.debt)
        assertThat(process.comment).isEqualTo(result.comment)
        assertThat(process.status).isEqualTo(result.status)
    }

    @Test
    fun `convert(request CreateProcessRequest) should execute successfully`() {
        val bid = createBid()
        val request = CreateProcessRequest(debt = BigDecimal.TEN, comment = "comment", bidId = bid.id!!)
        whenever(bidService.find(any())).thenReturn(bid)

        val result = processMapper.convert(request)

        assertThat(request.debt).isEqualTo(result.debt)
        assertThat(request.comment).isEqualTo(result.comment)
    }

    @Test
    fun `convert(dto ProcessDto) should execute successfully`() {
        val bid = createBid()
        val dto = ProcessDto(
            debt = BigDecimal.TEN, comment = "comment", status = ProcessStatus.IN_PROCESSING, bidId = bid.id!!
        )
        whenever(bidService.find(any())).thenReturn(bid)

        val result = processMapper.convert(dto)

        assertThat(dto.debt).isEqualTo(result.debt)
        assertThat(dto.comment).isEqualTo(result.comment)
        assertThat(dto.status).isEqualTo(result.status)
    }

    private fun createProcess(): Process {
        return Process(
            id = UUID.randomUUID(),
            debt = BigDecimal.valueOf(100),
            status = ProcessStatus.IN_PROCESSING,
            comment = "comment",
            bid = createBid()
        )
    }

    private fun createBid(): Bid {
        return Bid(
            id = UUID.randomUUID(),
            target = "target",
            coverLetter = "cover letter",
            date = LocalDateTime.now(),
            priority = BidPriority.MEDIUM,
            status = BidStatus.UNDER_CONSIDERATION
        )
    }
}