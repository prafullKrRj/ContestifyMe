package com.prafull.contestifyme.features.friendsFeature.domain.model

import com.google.gson.annotations.SerializedName

data class FriendsDetailsDto(
    @SerializedName("result")
    val result: List<FriendsInfoResultDto>,
    @SerializedName("status")
    val status: String
) {

}