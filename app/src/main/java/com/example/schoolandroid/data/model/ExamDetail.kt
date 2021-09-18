package com.example.schoolandroid.data.model

import com.google.gson.annotations.SerializedName

data class ExamDetail(
    @SerializedName("id")
    val id: Int,
    @SerializedName("author")
    val author: Author,
    @SerializedName("title")
    val title: String,
    @SerializedName("classroom")
    val classRoom: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("subject")
    val subject: String,
    @SerializedName("publish_time")
    val publishTime: Double,
    @SerializedName("edit_time")
    val editTime: Double,
    @SerializedName("count_task")
    val countTask: Int,
    @SerializedName("max_scores")
    val maxScores: Int,
    @SerializedName("comments")
    val comments: ArrayList<Comment>,
)
