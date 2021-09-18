package com.example.schoolandroid.data.model

import com.google.gson.annotations.SerializedName

data class ExamForStatistics(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String
)