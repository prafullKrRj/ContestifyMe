package com.prafull.contestifyme.onboard

import com.prafull.contestifyme.onboard.model.UsersInfo
import retrofit2.http.GET
import retrofit2.http.Url

interface OnBoardApiService {

    @GET
    suspend fun getUserInfo(
        @Url url: String,
    ): UsersInfo
}