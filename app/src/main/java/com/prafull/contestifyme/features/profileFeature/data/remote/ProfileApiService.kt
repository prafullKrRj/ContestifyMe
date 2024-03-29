package com.prafull.contestifyme.features.profileFeature.data.remote

import com.prafull.contestifyme.features.profileFeature.domain.model.ratingInfo.RatingDto
import com.prafull.contestifyme.features.profileFeature.domain.model.submissionsInfo.SubmissionDto
import com.prafull.contestifyme.features.profileFeature.domain.model.userInfo.ProfileUserDto
import retrofit2.http.GET
import retrofit2.http.Url

interface ProfileApiService {
    @GET
    suspend fun getUserInfoFromApi(
        @Url url: String
    ) : ProfileUserDto

    @GET
    suspend fun getUserRatingFromApi(
        @Url url: String
    ) : RatingDto

    @GET
    suspend fun getUserStatusFromApi(
        @Url url: String
    ) : SubmissionDto
}