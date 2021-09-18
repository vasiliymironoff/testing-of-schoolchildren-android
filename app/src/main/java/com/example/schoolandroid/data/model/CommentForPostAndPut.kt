package com.example.schoolandroid.data.model

import com.google.gson.annotations.SerializedName

data class CommentForPostAndPut(
    @SerializedName("exam")
    val exam: Int,
    @SerializedName("text")
    val text: String,
)
