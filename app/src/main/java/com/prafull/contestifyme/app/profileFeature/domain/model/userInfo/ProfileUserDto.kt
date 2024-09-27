package com.prafull.contestifyme.app.profileFeature.domain.model.userInfo

import com.google.gson.annotations.SerializedName
import com.prafull.contestifyme.onboard.model.UsersInfo

data class ProfileUserDto(
    @SerializedName("result")
    val result: List<ProfileResultDto>,
    @SerializedName("status")
    val status: String
) {
    fun toUserInfo() = UsersInfo(
        userResult = result.map { it.toUserResult() },
        status = status
    )
}