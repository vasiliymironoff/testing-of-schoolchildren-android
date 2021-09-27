package com.example.schoolandroid.data.model

import com.google.gson.annotations.SerializedName

data class StatisticsForExamStatistics(
    @SerializedName("id")
    val id: Int,
    @SerializedName("user")
    val user: Author,
    @SerializedName("exam")
    val exam: ExamForStatistics,
    @SerializedName("grade")
    val grade: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("start_time")
    val startTime: Double,
    @SerializedName("end_time")
    val endTime: Double,
    @SerializedName("errors")
    val errorsStatistics: List<ErrorStatistics>,
)
