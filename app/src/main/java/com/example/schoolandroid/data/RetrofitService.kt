package com.example.schoolandroid.data

import com.example.schoolandroid.data.model.EmailAndPassword
import com.example.schoolandroid.data.model.NewUser
import com.example.schoolandroid.data.model.ResponseNewUser
import com.example.schoolandroid.data.model.Token
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitService {

    @POST("/api/v1/auth/token/login/")
    suspend fun login(@Body emailAndPassword: EmailAndPassword): Token

    @POST("/api/v1/auth/users/")
    suspend fun registration(@Body newUser: NewUser): ResponseNewUser

}