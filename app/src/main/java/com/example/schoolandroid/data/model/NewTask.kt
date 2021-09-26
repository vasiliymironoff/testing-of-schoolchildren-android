package com.example.schoolandroid.data.model

import com.google.gson.annotations.SerializedName

data class NewTask(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("question")
    val question: String,
    @SerializedName("scores")
    val scores: Int,
    @SerializedName("answers")
    val answers: List<NewAnswer>
)
