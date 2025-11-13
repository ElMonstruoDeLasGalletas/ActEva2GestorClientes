package com.example.crm.util

object Validators {
    private val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
    private val PHONE_REGEX = Regex("^[0-9]{9,}$") // mínimo 9 dígitos

    fun isValidEmail(s: String) = s.matches(EMAIL_REGEX)
    fun isValidPhone(s: String) = s.matches(PHONE_REGEX)
    fun isNotBlank(vararg xs: String) = xs.all { it.trim().isNotEmpty() }
}

