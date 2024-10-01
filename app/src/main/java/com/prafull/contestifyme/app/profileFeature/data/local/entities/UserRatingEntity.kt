package com.prafull.contestifyme.app.profileFeature.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prafull.contestifyme.network.model.UserRating

@Entity(tableName = "user_rating")
data class UserRatingEntity(
    @PrimaryKey
    val uniqueId: String,
    val contestId: Int = 0,
    val contestName: String = "",
    val handle: String = "",
    val newRating: Int = 0,
    val oldRating: Int = 0,
    val rank: Int = 0,
    val ratingUpdateTimeSeconds: Int = 0
) {
    fun toUserRating(): UserRating = UserRating(
        uniqueId = uniqueId,
        contestId = contestId,
        contestName = contestName,
        handle = handle,
        newRating = newRating,
        oldRating = oldRating,
        rank = rank,
        ratingUpdateTimeSeconds = ratingUpdateTimeSeconds
    )
}