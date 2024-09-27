package com.prafull.contestifyme.onboard.model

import com.google.gson.annotations.SerializedName

data class UsersInfo(
    val userResult: List<UserResult>,
    @SerializedName("status")
    val status: String
) {

}