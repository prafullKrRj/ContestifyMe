package com.example.contestifyme.features.friendsFeature.model

import com.google.gson.annotations.SerializedName

data class FriendsDetailsDto(
    @SerializedName("result")
    val result: List<FriendsInfoResultDto>,
    @SerializedName("status")
    val status: String
) {

}