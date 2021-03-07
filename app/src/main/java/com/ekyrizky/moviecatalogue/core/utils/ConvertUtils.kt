package com.ekyrizky.moviecatalogue.core.utils

import java.text.SimpleDateFormat
import java.util.*

object ConvertUtils {
    fun getDateConverted(date: String): String {
        return if (date == "") {
            date
        } else {
            val apiDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formatDate = SimpleDateFormat("yyyy", Locale.getDefault())
            val year = apiDate.parse(date)
            formatDate.format(year!!)
        }
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