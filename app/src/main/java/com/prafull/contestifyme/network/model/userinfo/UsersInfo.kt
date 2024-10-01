package com.prafull.contestifyme.network.model.userinfo

import com.google.gson.annotations.SerializedName

data class UsersInfo(
    val result: List<UserResult>,
    @SerializedName("status")
    val status: String
)