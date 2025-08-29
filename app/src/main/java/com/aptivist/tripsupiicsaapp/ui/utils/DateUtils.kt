package com.aptivist.tripsupiicsaapp.ui.utils

import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

fun convertMillisToDate(millis: Long?): String {
    return millis?.let {
        formatDate(Date(it))
    } ?: throw IllegalArgumentException("Millis cannot be null")
}

fun formatDate(date: Date): String {
    val localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    return localDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
}

fun parseToDate(dateString: String): Date {
    val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
    val localDate = LocalDate.parse(dateString, formatter)
    return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
}

fun getTodaySystemDateInMillis(): Long {
    return LocalDate.now()
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}

fun fixUtcToLocalMillis(millis: Long?): Long? {
    if (millis == null) return null

    val localDate = java.time.Instant.ofEpochMilli(millis)
        .atZone(java.time.ZoneId.of("UTC"))
        .toLocalDate()

    return localDate
        .atStartOfDay(java.time.ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}

fun parseIso8601Date(dateString: String): Long? {
    return try {
        OffsetDateTime.parse(dateString).toInstant().toEpochMilli()
    } catch (e: Exception) {
        null
    }
}
