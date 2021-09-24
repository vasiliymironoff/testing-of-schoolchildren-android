package com.example.schoolandroid.data.model

import com.google.gson.annotations.SerializedName

data class NewExam(
    @SerializedName("title")
    val title: String,
    @SerializedName("classroom")
    val classRoom: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("subject")
    val subject: String,
    @SerializedName("tasks")
    val tasks: List<NewTask>
)
