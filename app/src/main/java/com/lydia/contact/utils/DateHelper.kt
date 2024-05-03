package com.lydia.contact.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun String.toReadableDate(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")

    val outputFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())

    return try {
        val date = inputFormat.parse(this)
        outputFormat.format(date)
    } catch (e: Exception) {
        e.localizedMessage ?: "Invalid date format"
    }
}