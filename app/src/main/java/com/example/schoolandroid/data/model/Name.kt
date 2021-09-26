package com.example.schoolandroid.data.model

import com.google.gson.annotations.SerializedName

data class Name(
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
)
