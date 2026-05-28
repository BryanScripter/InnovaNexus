package com.inovagab.data.remote.dto

data class LoginResponse(
    val token: String,
    val userId: String,
    val role: String,
    val name: String,
    val email: String
)
