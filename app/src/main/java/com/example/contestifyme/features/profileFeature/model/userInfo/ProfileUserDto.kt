package com.example.contestifyme.features.profileFeature.model.userInfo

import com.google.gson.annotations.SerializedName

data class ProfileUserDto(
    @SerializedName("result")
    val result: List<ProfileResultDto>,
    @SerializedName("status")
    val status: String
) {

}