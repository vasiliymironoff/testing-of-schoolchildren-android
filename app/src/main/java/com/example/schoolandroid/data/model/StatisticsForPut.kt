package com.example.schoolandroid.data.model

import com.google.gson.annotations.SerializedName

data class StatisticsForPut(
    @SerializedName("exam")
    val exam: Int,
    @SerializedName("grade")
    val grade: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("errors")
    val errorsStatistics: List<ErrorStatistics>,
)
