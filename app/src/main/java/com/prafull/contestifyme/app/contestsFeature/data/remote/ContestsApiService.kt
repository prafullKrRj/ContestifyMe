package com.prafull.contestifyme.app.contestsFeature.data.remote

import com.prafull.contestifyme.app.contestsFeature.domain.model.ContestsDto
import com.prafull.contestifyme.app.contestsFeature.domain.model.particularContest.ParticularContestDto
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


interface ContestsApiService {
    @GET("contest.list")
    suspend fun getContestsList(
        @Query("gym") gym: Boolean = false,
        @Query("page") page: Int = 1,
    ): ContestsDto

    @GET
    suspend fun getContestDetails(
        @Url url: String
    ): ParticularContestDto
}