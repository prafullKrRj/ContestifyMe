package com.prafull.contestifyme.network

import com.prafull.contestifyme.network.model.userinfo.UsersInfo
import com.prafull.contestifyme.network.model.userrating.RatingDto
import com.prafull.contestifyme.network.model.userstatus.SubmissionDto
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

interface CodeForcesApi {

    @GET
    suspend fun getUserInfo(
        @Url url: String
    ): UsersInfo

    @GET
    suspend fun getUserRating(
        @Url url: String
    ): RatingDto

    @GET
    suspend fun getStatus(
        @Url url: String
    ): SubmissionDto
}

val networkModule = module {

    single<CodeForcesApi> {
        Retrofit.Builder()
            .baseUrl("https://codeforces.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CodeForcesApi::class.java)
    }
}