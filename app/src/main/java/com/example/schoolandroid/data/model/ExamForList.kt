package com.example.schoolandroid.data.model

import com.google.gson.annotations.SerializedName

data class ExamForList(
    @SerializedName("id")
    val id: Int,
    @SerializedName("author")
    val author: Author,
    @SerializedName("title")
    val title: String,
    @SerializedName("classroom")
    val classRoom: Int,
    @SerializedName("subject")
    val subject: String,
    @SerializedName("publish_time")
    val publishTime: Double,
    @SerializedName("edit_time")
    val editTime: Double,


    )
