package com.prafull.contestifyme.features.contestsFeature.data.remote

import com.prafull.contestifyme.features.contestsFeature.domain.model.ContestsDto
import retrofit2.http.GET
import retrofit2.http.Query


interface ContestsApiService {
    @GET("contest.list")
    suspend fun getContestsList(
        @Query("gym") gym: Boolean = false,
        @Query("page") page: Int = 1,
    ): ContestsDto
}