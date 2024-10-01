package com.prafull.contestifyme.app.profileFeature.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prafull.contestifyme.network.model.userinfo.UserResult

@Entity(tableName = "user_info")
data class UserInfoEntity(
    @PrimaryKey val handle: String,
    val avatar: String,
    val city: String,
    val contribution: Int,
    val country: String,
    val firstName: String,
    val friendOfCount: Int,
    val lastName: String,
    val lastOnlineTimeSeconds: Int,
    val maxRank: String,
    val maxRating: Int,
    val organization: String,
    val rank: String,
    val rating: Int,
    val registrationTimeSeconds: Int,
    val titlePhoto: String
) {
    fun toUserResult() = UserResult(
        avatar = avatar,
        contribution = contribution,
        country = country,
        firstName = firstName,
        lastName = lastName,
        city = city,
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