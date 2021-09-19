package com.example.schoolandroid.data.model

import com.google.gson.annotations.SerializedName

data class StatisticsResponsePostAndPut(
    @SerializedName("id")
    val id: Int,
    @SerializedName("exam")
    val exam: Int,
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
