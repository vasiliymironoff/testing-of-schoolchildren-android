package com.example.schoolandroid.data

import com.example.schoolandroid.data.model.*
import retrofit2.Response
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

    @GET("/api/v1/exams-detail/{id}/")
    suspend fun getExamDetail(@Path("id") id: Int): ExamWithTaskForRetrieve

    @POST("/api/v1/exams-detail/")
    suspend fun postExamDetail(@Body newExam: NewExam)

    @PUT("/api/v1/exams-detail/{id}/")
    suspend fun putExamDetail(@Path("id") id: Int, @Body putExam: NewExam)

    @GET("/api/v1/exams-me/")
    suspend fun getMyExams(): List<ExamForList>

    @PUT("/api/v1/profiles/{id}/")
    suspend fun putProfile(@Path("id") id: Int, @Body avatar: Avatar): Avatar

    @PUT("/api/v1/auth/users/me/")
    suspend fun putMe(@Body name: Name)

    @POST("/api/v1/auth/users/set_email/")
    suspend fun postEmail(@Body email: Email)

    @POST("/api/v1/auth/users/set_password/")
    suspend fun postPassword(@Body password: Password)

    @DELETE("/api/v1/tasks/{id}/")
    suspend fun deleteTask(@Path("id") id: Int): Response<Void>

    @DELETE("/api/v1/answers/{id}/")
    suspend fun deleteAnswer(@Path("id") id: Int): Response<Void>

    @DELETE("/api/v1/exams-detail/{id}/")
    suspend fun deleteExam(@Path("id") id: Int): Response<Void>

    @GET("/api/v1/exams-statistics/{id}/")
    suspend fun getExamStatistics(@Path("id") id: Int): ExamStatistics
}