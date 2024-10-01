package com.prafull.contestifyme.app.problemsFeature.data.remote

import com.prafull.contestifyme.app.problemsFeature.domain.model.ProblemsDto
import com.prafull.contestifyme.app.problemsFeature.domain.model.acmsguru.AcmsguruDto
import retrofit2.http.GET
import retrofit2.http.Url

interface ProblemsApiService {

    @GET
    suspend fun getProblems(
        @Url url: String
    ): ProblemsDto

    @GET
    suspend fun getAcmsGuruProblems(
        @Url url: String
    ): AcmsguruDto
}