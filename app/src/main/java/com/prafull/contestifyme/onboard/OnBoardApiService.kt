package com.prafull.contestifyme.onboard

import com.prafull.contestifyme.network.model.userinfo.UsersInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface OnBoardApiService {

    @GET("user.info")
    suspend fun getUser(
        @Query("handles") handle: String,
    ): UsersInfo
}