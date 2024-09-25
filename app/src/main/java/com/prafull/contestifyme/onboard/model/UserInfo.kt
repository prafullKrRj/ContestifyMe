package com.prafull.contestifyme.onboard.model

import com.google.gson.annotations.SerializedName

data class UserInfo(
    val result: List<Result>,
    @SerializedName("status")
    val status: String
)