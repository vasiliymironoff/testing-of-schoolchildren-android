package com.example.schoolandroid.data.model

import com.google.gson.annotations.SerializedName

data class Email(
    @SerializedName("new_email")
    val email: String,
    @SerializedName("current_password")
    val password: String,
)
