package com.example.schoolandroid.data.model

import com.google.gson.annotations.SerializedName
import java.net.URL

class Me(@SerializedName("first_name") val firstName: String,
         @SerializedName("last_name") val lastName: String,
         @SerializedName("email") val email: String,
         @SerializedName("is_teacher") val isTeacher: Boolean,
         @SerializedName("avatar") val avatar: URL) {
}