package com.example.contestifyme.features.profileFeature.data.remote

import com.example.contestifyme.features.profileFeature.model.userInfo.ProfileUserDto
import retrofit2.http.GET
import retrofit2.http.Url

interface ProfileApiService {
    @GET
    suspend fun getUserInfoFromApi(
        @Url url: String
    ) : ProfileUserDto
}