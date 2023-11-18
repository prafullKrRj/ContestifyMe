package com.example.contestifyme.startOnBoard.network.model

import com.google.gson.annotations.SerializedName

data class userInfo(
    val result: List<Result>,
    @SerializedName("status")
    val status: String
)