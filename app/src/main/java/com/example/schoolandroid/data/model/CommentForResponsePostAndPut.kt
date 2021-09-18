package com.example.schoolandroid.data.model

import com.google.gson.annotations.SerializedName

data class CommentForResponsePostAndPut(
    @SerializedName("id")
    public val id: Int,
    @SerializedName("exam")
    public val exam: Int,
    @SerializedName("text")
    public val text: String,
    @SerializedName("publish_time")
    public val publishTime: Double,
    @SerializedName("edit_time")
    public val editTime: Double,
)
