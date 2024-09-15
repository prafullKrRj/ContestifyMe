package com.prafull.contestifyme.features.friendsFeature.data.local

import androidx.room.Entity
import com.prafull.contestifyme.features.friendsFeature.domain.model.FriendsInfoResultDto

@Entity(tableName = "friends", primaryKeys = ["handle"])
data class FriendsDataEntity(
    val avatar: String?,
    val contribution: Int?,
    val country: String?,
    val firstName: String?,
    val friendOfCount: Int?,
    val handle: String,
    val lastOnlineTimeSeconds: Int?,
    val maxRank: String?,
    val maxRating: Int?,
    val organization: String?,
    val rank: String?,
    val rating: Int?,
    val registrationTimeSeconds: Int?,
    val titlePhoto: String?
) {
    fun toFriendsInfoDto(): FriendsInfoResultDto {
        return FriendsInfoResultDto(
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