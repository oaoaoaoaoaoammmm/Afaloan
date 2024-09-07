package com.example.afaloan

import com.example.afaloan.configurations.s3.S3Properties
import com.example.afaloan.configurations.security.AuthenticationProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(S3Properties::class, AuthenticationProperties::class)
class AfaloanApplication

//  TODO
//      0   A0 - Init commit infrastructure -> DONE
//      1   А1 - ProfileService и ProfileController и ProfileMapper -> DONE
//      2   А2 - BoilingPoint и BoilingPointService и BoilingPointsMapper и BoilingPointsController -> DONE
//      3   А3 - Microloan и MicroloanService и MicroloanMapper и MicroloanController -> DONE
//      4   А4 - Bid и BidService и BidMapper и BidController -> DONE
//      5   А5 - Добавить @PreAuthorize -> DONE
//      6   А6 - Process и ProcessService и ProcessMapper и ProcessController -> DONE
//      7   A7 - Рефакторинг тестов -> DONE
//      8
//      9
//      10

fun main(args: Array<String>) {
    runApplication<AfaloanApplication>(*args)
}
