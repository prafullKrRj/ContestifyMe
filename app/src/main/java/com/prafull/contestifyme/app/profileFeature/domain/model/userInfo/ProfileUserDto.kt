package com.prafull.contestifyme.app.profileFeature.domain.model.userInfo

import com.google.gson.annotations.SerializedName

data class ProfileUserDto(
    @SerializedName("result")
    val result: List<ProfileResultDto>,
    @SerializedName("status")
    val status: String
)