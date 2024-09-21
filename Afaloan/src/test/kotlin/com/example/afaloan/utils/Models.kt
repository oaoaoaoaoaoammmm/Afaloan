package com.example.afaloan.utils

import com.example.afaloan.controller.orders.dtos.CreateOrderRequest
import com.example.afaloan.controller.boilingpoints.dtos.CreateBoilingPointRequest
import com.example.afaloan.controller.boilingpoints.dtos.UpdateBoilingPointRequest
import com.example.afaloan.controller.microloans.dtos.MicroloanDto
import com.example.afaloan.controller.processes.dtos.CreateProcessRequest
import com.example.afaloan.controller.processes.dtos.ProcessDto
import com.example.afaloan.controller.profiles.dtos.CreateProfileRequest
import com.example.afaloan.controller.profiles.dtos.UpdateProfileRequest
import com.example.afaloan.models.Order
import com.example.afaloan.models.User
import com.example.afaloan.models.BoilingPoint
import com.example.afaloan.models.Microloan
import com.example.afaloan.models.Process
import com.example.afaloan.models.Profile
import com.example.afaloan.models.enumerations.OrderPriority
import com.example.afaloan.models.enumerations.OrderStatus
import com.example.afaloan.models.enumerations.ProcessStatus
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

const val ENCODED_USER_PASSWORD = "\$2a\$10\$.IEUyyxTZjIGYnDHOcFW3e8AD5QFAKWj7nu7NM1NfBs.wE6AtC83a"

var USER = User(
    id = UUID.randomUUID(),
    username = "john.doe@mail.ru",
    password = ENCODED_USER_PASSWORD,
    confirmed = true,
    confirmedUsername = true,
    blocked = false,
    roles = setOf()
)

/**
 * Profile
 */

var PROFILE = Profile(
    id = UUID.randomUUID(),
    name = "name",
    surname = "surname",
    patronymic = "patronymic",
    phoneNumber = "+79832422045",
    passportSeries = "1234",
    passportNumber = "123456",
    monthlyIncome = BigDecimal.TEN,
    user = USER
)

fun createCreateProfileRequest() = CreateProfileRequest(
    name = "name",
    surname = "surname",
    patronymic = "patronymic",
    phoneNumber = "+79832422045",
    passportSeries = "1234",
    passportNumber = "123456",
    monthlyIncome = BigDecimal.TEN,
    userId = USER.id!!
)

fun createUpdateProfileRequest() = UpdateProfileRequest(
    name = "name",
    surname = "surname",
    patronymic = "patronymic",
    phoneNumber = "+79832422045",
    passportSeries = "1234",
    passportNumber = "123456",
    monthlyIncome = BigDecimal.TEN,
    userId = USER.id!!
)

/**
 * Microloan
 */

fun createMicroloan() = Microloan(
    id = UUID.randomUUID(),
    name = "name",
    sum = BigDecimal.valueOf(100),
    monthlyInterest = BigDecimal.ZERO,
    conditions = "conditions",
    monthlyIncomeRequirement = BigDecimal.TWO,
    otherRequirements = "other requirements"
)

fun createMicroloanDto() = MicroloanDto(
    name = "name",
    sum = BigDecimal.TWO,
    monthlyInterest = BigDecimal.ZERO,
    conditions = "conditions",
    monthlyIncomeRequirement = BigDecimal.ONE,
    otherRequirements = "other requirements"
)

/**
 * BoilingPoint
 */

fun createBoilingPoint() = BoilingPoint(
    id = UUID.randomUUID(),
    city = "Taishet",
    address = "Cherniy vanya 3a",
    openingHours = "Пн-Пт: 09:00-18:00, Сб: 10:00-14:00, Вс: выходной",
    info = "info"
)

fun createCreateBoilingPointRequest() = CreateBoilingPointRequest(
    city = "Taishet",
    address = "Cherniy vanya 3a",
    openingHours = "Пн-Пт: 09:00-18:00, Сб: 10:00-14:00, Вс: выходной",
    info = "info"
)

fun createUpdateBoilingPointRequest() = UpdateBoilingPointRequest(
    city = "Taishet",
    address = "Cherniy vanya 3a",
    openingHours = "Пн-Пт: 09:00-18:00, Сб: 10:00-14:00, Вс: выходной",
    info = "info"
)

/**
 * Order
 */

fun createOrder(
    profile: Profile = PROFILE,
    microloan: Microloan = createMicroloan(),
    boilingPoint: BoilingPoint = createBoilingPoint()
) = Order(
    id = UUID.randomUUID(),
    target = "target",
    coverLetter = "cover letter",
    date = LocalDateTime.now(),
    priority = OrderPriority.MEDIUM,
    status = OrderStatus.UNDER_CONSIDERATION,
    employeeMessage = "employee message",
    profile = profile,
    microloan = microloan,
    boilingPoint = boilingPoint
)

fun createCreateOrderRequest(order: Order = createOrder()) = CreateOrderRequest(
    target = order.target,
    coverLetter = order.coverLetter,
    priority = order.priority,
    employeeMessage = order.employeeMessage,
    profileId = order.profile!!.id!!,
    microloanId = order.microloan!!.id!!,
    boilingPointId = order.boilingPoint!!.id!!
)

/**
 * Process
 */

fun createProcess(order: Order = createOrder()) = Process(
    id = UUID.randomUUID(),
    debt = order.microloan!!.sum,
    status = ProcessStatus.IN_PROCESSING,
    comment = "comment",
    order = order
)

fun createCreateProcessRequest(process: Process = createProcess()) = CreateProcessRequest(
    debt = process.debt,
    comment = process.comment,
    orderId = process.order!!.id!!
)

fun createProcessDto(process: Process = createProcess()) = ProcessDto(
    debt = process.debt,
    comment = process.comment,
    status = ProcessStatus.IN_PROCESSING,
    orderId = process.order!!.id!!
)
