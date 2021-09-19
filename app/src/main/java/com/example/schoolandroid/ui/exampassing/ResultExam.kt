package com.example.schoolandroid.ui.exampassing

import com.example.schoolandroid.data.model.ErrorStatistics

data class ResultExam(
    val grade: Int,
    val total: Int,
    val errors: List<ErrorStatistics>
)
