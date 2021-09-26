package com.example.schoolandroid.data.model

import com.google.gson.annotations.SerializedName

data class Password(
    @SerializedName("current_password")
    val currentPassword: String,
    @SerializedName("new_password")
    val newPassword: String,
)
