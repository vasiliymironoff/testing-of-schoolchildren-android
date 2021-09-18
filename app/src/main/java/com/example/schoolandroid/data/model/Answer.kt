package com.example.schoolandroid.data.model

import com.google.gson.annotations.SerializedName

data class Answer(
    @SerializedName("id")
    val id: Int,
    @SerializedName("text")
    val text: String,
    @SerializedName("is_correct")
    val isCorrect: Boolean
)
