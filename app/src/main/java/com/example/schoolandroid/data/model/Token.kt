package com.example.schoolandroid.data.model

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("auth_token")
    val token: String,
)
