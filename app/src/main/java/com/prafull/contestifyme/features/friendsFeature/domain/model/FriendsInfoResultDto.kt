package com.prafull.contestifyme.features.friendsFeature.domain.model

import com.google.gson.annotations.SerializedName
import com.prafull.contestifyme.features.friendsFeature.data.local.FriendsDataEntity

data class FriendsInfoResultDto(
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("contribution") val contribution: Int?,
    @SerializedName("country") val country: String?,
    @SerializedName("firstName") val firstName: String?,
    @SerializedName("friendOfCount") val friendOfCount: Int?,
    @SerializedName("handle") val handle: String,
    @SerializedName("lastOnlineTimeSeconds") val lastOnlineTimeSeconds: Int?,
    @SerializedName("maxRank") val maxRank: String?,
    @SerializedName("maxRating") val maxRating: Int?,
    @SerializedName("organization") val organization: String?,
    @SerializedName("rank") val rank: String?,
    @SerializedName("rating") val rating: Int?,
    @SerializedName("registrationTimeSeconds") val registrationTimeSeconds: Int?,
    @SerializedName("titlePhoto") val titlePhoto: String?
) {
    fun toFriendsDataEntity(): FriendsDataEntity {
        return FriendsDataEntity(
            avatar = avatar,
            contribution = contribution,
            country = country,
            firstName = firstName,
            friendOfCount = friendOfCount,
            handle = handle,
            lastOnlineTimeSeconds = lastOnlineTimeSeconds,
            maxRank = maxRank,
            maxRating = maxRating,
            organization = organization,
            rank = rank,
            rating = rating,
            registrationTimeSeconds = registrationTimeSeconds,
            titlePhoto = titlePhoto
        )
    }
}