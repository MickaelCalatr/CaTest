package com.antartic.sudio.domain.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

@SuppressLint("SimpleDateFormat")
fun getDate(timestamp: Long): String? = try {
    val date = Date(timestamp * 1000L)
    val sdf = SimpleDateFormat("dd/MM/yyyy")
    sdf.timeZone = TimeZone.getDefault()
    sdf.format(date).toString()
} catch (e: IllegalArgumentException) {
    null
}

fun getDate(date: String): String? = try {
    val timestamp = date.toLong()
    getDate(timestamp)
} catch (e: NumberFormatException) {
    date
}
