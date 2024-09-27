package com.prafull.contestifyme.app.friendsFeature.data.network

import com.prafull.contestifyme.app.profileFeature.domain.model.ratingInfo.RatingDto
import com.prafull.contestifyme.app.profileFeature.domain.model.submissionsInfo.SubmissionDto
import com.prafull.contestifyme.app.profileFeature.domain.model.userInfo.ProfileUserDto
import retrofit2.http.GET
import retrofit2.http.Url

interface FriendApiService {

    @GET
    suspend fun getFriendsData(
        @Url url: String
    ): ProfileUserDto

    @GET
    suspend fun getFriendInfo(
        @Url url: String
    ): ProfileUserDto


    @GET
    suspend fun getFriendRating(
        @Url url: String
    ): RatingDto

    @GET
    suspend fun getFriendSubmissions(
        @Url url: String
    ): SubmissionDto
}