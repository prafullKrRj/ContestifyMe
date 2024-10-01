package com.prafull.contestifyme.network.model.userinfo

import com.google.gson.annotations.SerializedName
import com.prafull.contestifyme.app.profileFeature.data.local.entities.UserInfoEntity

data class UserResult(
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("city") val city: String?,
    @SerializedName("contribution") val contribution: Int?,
    @SerializedName("country") val country: String?,
    @SerializedName("firstName") val firstName: String?,
    @SerializedName("friendOfCount") val friendOfCount: Int?,
    @SerializedName("handle") val handle: String,
    @SerializedName("lastName") val lastName: String?,
    @SerializedName("lastOnlineTimeSeconds") val lastOnlineTimeSeconds: Int?,
    @SerializedName("maxRank") val maxRank: String?,
    @SerializedName("maxRating") val maxRating: Int?,
    @SerializedName("organization") val organization: String?,
    @SerializedName("rank") val rank: String?,
    @SerializedName("rating") val rating: Int?,
    @SerializedName("registrationTimeSeconds") val registrationTimeSeconds: Int?,
    @SerializedName("titlePhoto") val titlePhoto: String?
) {
    fun toUserInfoEntity(): UserInfoEntity {
        return UserInfoEntity(
            handle = handle,
            avatar = avatar ?: "",
            contribution = contribution ?: 0,
            country = country ?: "",
            city = city ?: "",
            friendOfCount = friendOfCount ?: 0,
            lastOnlineTimeSeconds = lastOnlineTimeSeconds ?: 0,
            maxRank = maxRank ?: "",
            maxRating = maxRating ?: 0,
            organization = organization ?: "",
            firstName = firstName ?: "",
            lastName = lastName ?: "",
            rank = rank ?: "",
            rating = rating ?: 0,
            registrationTimeSeconds = registrationTimeSeconds ?: 0,
            titlePhoto = titlePhoto ?: ""
        )
    }
}