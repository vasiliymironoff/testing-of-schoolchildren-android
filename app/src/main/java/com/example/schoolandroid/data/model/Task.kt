package com.example.schoolandroid.data.model

import com.google.gson.annotations.SerializedName

data class Task(
    @SerializedName("id")
    val id: Int,
    @SerializedName("question")
    val question: String,
    @SerializedName("answers")
    val answers: List<Answer>,
    @SerializedName("scores")
    val scores: Int,
    @SerializedName("many_option")
    val manyOption: Boolean,
)
