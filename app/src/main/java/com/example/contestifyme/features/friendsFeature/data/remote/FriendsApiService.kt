package com.example.contestifyme.features.friendsFeature.data.remote

import com.example.contestifyme.features.friendsFeature.model.FriendsDetailsDto
import retrofit2.http.GET
import retrofit2.http.Url

interface FriendsApiService {

    @GET
    suspend fun getFriendsData(
        @Url url: String
    ): FriendsDetailsDto
}