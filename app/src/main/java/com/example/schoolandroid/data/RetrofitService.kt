package com.example.schoolandroid.data

import com.example.schoolandroid.data.model.*
import retrofit2.http.*

interface RetrofitService {

    @POST("/api/v1/auth/token/login/")
    suspend fun login(@Body emailAndPassword: EmailAndPassword): Token

    @POST("/api/v1/auth/users/")
    suspend fun registration(@Body newUser: NewUser): ResponseNewUser

    @GET("/api/v1/auth/users/me/")
    suspend fun getMe(): Me

    @GET("/api/v1/exams/")
    suspend fun getListExams(): List<ExamForList>

    @GET("/api/v1/exams/{id}/")
    suspend fun getExam(@Path("id") id: Int): ExamDetail

    @GET("/api/v1/statistics/")
    suspend fun getListStatistics(): List<Statistics>

    @POST("/api/v1/statistics/")
    suspend fun postStatistics(@Body statisticsForPost: StatisticsForPost): StatisticsResponsePostAndPut

    @PUT("/api/v1/statistics/{id}/")
    suspend fun putStatistics(@Path("id") idStatistics: Int, @Body statistics: StatisticsForPut): StatisticsResponsePostAndPut

    @POST("/api/v1/comments/")
    suspend fun postComment(@Body comment: CommentForPostAndPut): CommentForResponsePostAndPut

    @PUT("/api/v1/comments/{id}/")
    suspend fun putComment(
        @Path("id") idComment: Int,
        @Body comment: CommentForPostAndPut
    ): CommentForResponsePostAndPut

    @GET("api/v1/exams-detail/{id}/")
    suspend fun getExamDetail(@Path("id") id: Int): ExamWithTaskForRetrieve
}