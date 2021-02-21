package com.ekyrizky.moviecatalogue.utils

import java.text.SimpleDateFormat
import java.util.*

object ConvertUtils {
    fun getDateConverted(date: String): String {
        val apiDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formatDate = SimpleDateFormat("yyyy", Locale.getDefault())
        val year = apiDate.parse(date)
        return formatDate.format(year!!)
    }

    fun getRuntimeConverted(time: Int): String {
        var hour = 0
        var minute = time
        var result = "$minute min"

        while (minute >= 60) {
            hour += 1
            minute -= 60
            result = "$hour h $minute min"
        }

        return result
    }
}