package com.example.contestifyme.features.profileFeature.model.userInfo

import com.google.gson.annotations.SerializedName

data class ProfileResultDto(
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("contribution") val contribution: Int?,
    @SerializedName("country") val country: String?,
    @SerializedName("firstName") val firstName: String?,
    @SerializedName("friendOfCount") val friendOfCount: Int?,
    @SerializedName("handle") val handle: String?,
    @SerializedName("lastOnlineTimeSeconds") val lastOnlineTimeSeconds: Int?,
    @SerializedName("maxRank") val maxRank: String?,
    @SerializedName("maxRating") val maxRating: Int?,
    @SerializedName("organization") val organization: String?,
    @SerializedName("rank") val rank: String?,
    @SerializedName("rating") val rating: Int?,
    @SerializedName("registrationTimeSeconds") val registrationTimeSeconds: Int?,
    @SerializedName("titlePhoto") val titlePhoto: String?
) {

}