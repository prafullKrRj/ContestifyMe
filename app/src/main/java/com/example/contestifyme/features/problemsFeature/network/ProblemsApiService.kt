package com.example.contestifyme.features.problemsFeature.network

import com.example.contestifyme.features.problemsFeature.model.ProblemsDto
import retrofit2.http.GET
import retrofit2.http.Url

interface ProblemsApiService {

    @GET
    suspend fun getProblems(
        @Url url: String
    ): ProblemsDto
}