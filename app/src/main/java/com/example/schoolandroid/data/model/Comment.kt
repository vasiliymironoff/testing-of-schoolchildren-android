package com.example.schoolandroid.data.model

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("id")
    val id: Int,
    @SerializedName("author")
    val author: Author,
    @SerializedName("text")
    val text: String,
    @SerializedName("publish_time")
    val publishTime: Double,
    @SerializedName("edit_time")
    val editTime: Double,
)
