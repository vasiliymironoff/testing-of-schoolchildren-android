package com.example.schoolandroid.data.model

import com.google.gson.annotations.SerializedName

data class NewAnswer(
    @SerializedName("text")
    val text: String,
    @SerializedName("is_correct")
    val isCorrect: Boolean,
)
