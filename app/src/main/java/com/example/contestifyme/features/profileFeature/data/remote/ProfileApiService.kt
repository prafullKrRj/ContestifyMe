package com.example.contestifyme.features.profileFeature.data.remote

import com.example.contestifyme.features.profileFeature.model.ratingInfo.RatingDto
import com.example.contestifyme.features.profileFeature.model.submissionsInfo.SubmissionDto
import com.example.contestifyme.features.profileFeature.model.userInfo.ProfileUserDto
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