package com.example.schoolandroid.util

import java.text.SimpleDateFormat
import java.util.*

object Util {
    fun utilTimeToFormatForUI(unitTime: Double): String {
        val format = SimpleDateFormat("HH:mm dd.MM Y")
        val date = Date(((unitTime * 1000).toLong()))
        return format.format(date)
    }
}