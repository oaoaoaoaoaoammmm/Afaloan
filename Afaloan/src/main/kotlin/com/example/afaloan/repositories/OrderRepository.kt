package com.example.afaloan.repositories

import com.example.afaloan.models.Order
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface OrderRepository: JpaRepository<Order, UUID> {

    @Query(value = """
        select order from Order order
        left join fetch order.profile
        left join fetch order.microloan
        left join fetch order.boilingPoint
        where order.id = :id
    """)
    override fun findById(id: UUID): Optional<Order>

    fun findPageByBoilingPointId(boilingPointId: UUID, pageable: Pageable): Page<Order>

    fun findPageByProfileId(profileId: UUID, pageable: Pageable): Page<Order>

    fun findPageByMicroloanId(microloanId: UUID, pageable: Pageable): Page<Order>
}