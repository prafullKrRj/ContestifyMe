package com.prafull.contestifyme.features.friendsFeature.data.remote

import com.prafull.contestifyme.features.friendsFeature.model.FriendsDetailsDto
import com.prafull.contestifyme.features.profileFeature.model.ratingInfo.RatingDto
import com.prafull.contestifyme.features.profileFeature.model.submissionsInfo.SubmissionDto
import retrofit2.http.GET
import retrofit2.http.Url

interface FriendsApiService {

    @GET
    suspend fun getFriendsData(
        @Url url: String
    ): FriendsDetailsDto

    @GET
    suspend fun getUserRatingFromApi(
        @Url url: String
    ) : RatingDto

    @GET
    suspend fun getUserStatusFromApi(
        @Url url: String
    ) : SubmissionDto
}