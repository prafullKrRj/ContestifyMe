package com.prafull.contestifyme.network.model

import com.prafull.contestifyme.app.profileFeature.data.local.entities.UserRatingEntity

data class UserRating(
    val uniqueId: String,
    val contestId: Int = 0,
    val contestName: String = "",
    val handle: String = "",
    val newRating: Int = 0,
    val oldRating: Int = 0,
    val rank: Int = 0,
    val ratingUpdateTimeSeconds: Int = 0
) {
    fun toUserRatingEntity() = UserRatingEntity(
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