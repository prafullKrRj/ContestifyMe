package com.example.contestifyme.startOnBoard.network

import com.example.contestifyme.startOnBoard.network.model.userInfo
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {

    @GET
    suspend fun getUserInfo(
        @Url url: String,
    ) : userInfo
}