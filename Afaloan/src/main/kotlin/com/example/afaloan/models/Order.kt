package com.example.afaloan.models

import com.example.afaloan.models.enumerations.OrderPriority
import com.example.afaloan.models.enumerations.OrderStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "orders")
data class Order(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(name = "target")
    val target: String,

    @Column(name = "cover_letter")
    val coverLetter: String? = null,

    @Column(name = "date")
    val date: LocalDateTime,

    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    val priority: OrderPriority,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    val status: OrderStatus,

    @Column(name = "employee_message")
    val employeeMessage: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    val profile: Profile? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "microloan_id", referencedColumnName = "id")
    val microloan: Microloan? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boiling_point_id", referencedColumnName = "id")
    val boilingPoint: BoilingPoint? = null
)