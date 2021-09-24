package com.example.schoolandroid.util

import android.content.Context
import android.net.ConnectivityManager
import com.example.schoolandroid.R
import java.text.SimpleDateFormat
import java.util.*

object Util {
    fun utilTimeToFormatForUI(unitTime: Double): String {
        val format = SimpleDateFormat("HH:mm dd.MM Y")
        val date = Date(((unitTime * 1000).toLong()))
        return format.format(date)
    }

    public fun isNetworkAvailable(context: Context): Boolean {
        val cm: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    public fun percentToColor(percent: Int): Int {
        return when (percent / 10 * 10) {
            10 -> R.color.percent_10
            20 -> R.color.percent_20
            30 -> R.color.percent_30
            40 -> R.color.percent_40
            50 -> R.color.percent_50
            60 -> R.color.percent_60
            70 -> R.color.percent_70
            80 -> R.color.percent_80
            90 -> R.color.percent_90
            100 -> R.color.percent_100
            else -> R.color.percent_10
        }
    }
    fun getSubjectFromAbbreviation(ab: String) =
        when (ab) {
            "al" -> "Алгебра"
            "as" -> "Астрономия"
            "bi" -> "Биология"
            "ch" -> "Химия"
            "en" -> "Английский"
            "gm" -> "Геометрия"
            "hi" -> "История"
            "ph" -> "Физика"
            "ru" -> "Русский язык"
            "cs" -> "Информатика"
            "ss" -> "Обществознание"
            "gg" -> "География"
            "fl" -> "Иностранный язык"
            "li" -> "Литература"
            "ob" -> "ОБЖ"
            else -> "Другой предмет"
        }
    fun getAbbreviationFromSubject(subject: String) =
        when (subject) {
            "Алгебра"  -> "al"
            "Астрономия" -> "as"
            "Биология"-> "bi"
            "Химия" -> "ch"
            "Английский" -> "en"
            "Геометрия" -> "gm"
            "История" -> "hi"
            "Физика" -> "ph"
            "Русский язык" -> "ru"
            "Информатика" -> "cs"
            "Обществознание" -> "ss"
            "География"  -> "gg"
            "Иностранный язык"-> "fl"
            "Литература" -> "li"
            "ОБЖ" -> "ob"
            else -> "dp"
        }
}