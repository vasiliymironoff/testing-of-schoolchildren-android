package com.example.schoolandroid.data.model

import com.google.gson.annotations.SerializedName

data class Statistics(
    @SerializedName("id")
    val id: Int,
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
    val errors: List<String>,
)
