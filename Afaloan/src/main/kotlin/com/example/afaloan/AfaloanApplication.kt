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
//      1   UserController и UserMapper
//      2   При удалении документа удалять его так же и в Profile -> documents (jsonb)
//      3   ProfileService и ProfileController и ProfileMapper
//      4
//      5
//      6
//      7
//      8
//      9
//      10
//      ...
//      n   Тесты

fun main(args: Array<String>) {
    runApplication<AfaloanApplication>(*args)
}
