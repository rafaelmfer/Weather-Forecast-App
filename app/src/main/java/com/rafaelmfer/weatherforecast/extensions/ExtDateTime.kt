package com.rafaelmfer.weatherforecast.extensions

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

private const val serverPattern = "yyyy-MM-dd HH:mm"

fun get12hoursTime(date: String): String? {
    val inputFormat = SimpleDateFormat(serverPattern, Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("h:mm a", Locale.ENGLISH)

    val time = inputFormat.parse(date)

    return time?.let { outputFormat.format(it) }
}

fun getDayOfWeekWithFullDate(date: String): String {
    val inputFormat = SimpleDateFormat(serverPattern, Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)


    val fullDate = inputFormat.parse(date)
    val dateOnly = LocalDate.parse(date, DateTimeFormatter.ofPattern(serverPattern))
    val dayOfWeek = dateOnly.dayOfWeek.toString()
        .lowercase(Locale.getDefault())
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

    return "$dayOfWeek, ${fullDate?.let { outputFormat.format(it) }}"
}