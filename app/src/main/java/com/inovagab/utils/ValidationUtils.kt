package com.inovagab.utils

object ValidationUtils {

    fun isValidEmail(email: String): Boolean {
        return email.contains("@")
    }

    fun isNotEmpty(value: String): Boolean {
        return value.isNotBlank()
    }
}
