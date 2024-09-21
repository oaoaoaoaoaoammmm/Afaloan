package com.example.afaloan.exceptions

enum class ErrorCode {

    // common errors
    INVALID_REQUEST,
    SERVICE_UNAVAILABLE,

    // user errors
    FORBIDDEN,
    USER_NOT_FOUND,
    ROLE_NOT_FOUND,
    USER_ALREADY_EXISTS,

    // profile errors
    PROFILE_NOT_FOUND,

    // boiling point errors
    BOILING_POINT_NOT_FOUND,

    // microloan errors
    MICROLOAN_NOT_FOUND,

    // order errors
    ORDER_NOT_FOUND,

    // process errors
    PROCESS_NOT_FOUND
}