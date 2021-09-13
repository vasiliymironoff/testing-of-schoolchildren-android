package com.example.schoolandroid.data.model

import com.google.gson.annotations.SerializedName

data class ResponseNewUser(
    @SerializedName("id") val id: Integer,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("is_teacher") val isTeacher: Boolean,
    @SerializedName("email") val email: String,
)
