package com.inovagab.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {

    private const val DEFAULT_PATTERN = "dd/MM/yyyy HH:mm"

    fun formatTimestamp(timestamp: Long, pattern: String = DEFAULT_PATTERN): String {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        return formatter.format(Date(timestamp))
    }
}
