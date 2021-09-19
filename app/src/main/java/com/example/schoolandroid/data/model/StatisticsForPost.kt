package com.example.schoolandroid.data.model

import com.google.gson.annotations.SerializedName

data class StatisticsForPost(
    @SerializedName("exam")
    val exam: Int,
    @SerializedName("grade")
    val grade: Int,
    @SerializedName("total")
    val total: Int,
)
