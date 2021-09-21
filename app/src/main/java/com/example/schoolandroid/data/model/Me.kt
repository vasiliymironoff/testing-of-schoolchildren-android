package com.example.schoolandroid.data.model

import android.net.Uri
import com.google.gson.annotations.SerializedName
import java.net.URL

class Me(
    @SerializedName("id")
    public val id: Int,
    @SerializedName("first_name")
    public val firstName: String,
    @SerializedName("last_name")
    public val lastName: String,
    @SerializedName("email")
    public val email: String,
    @SerializedName("is_teacher")
    public val isTeacher: Boolean,
    @SerializedName("avatar")
    public var avatar: String?
) {
}