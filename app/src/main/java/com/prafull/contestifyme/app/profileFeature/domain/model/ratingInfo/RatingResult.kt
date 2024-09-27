package com.prafull.contestifyme.app.profileFeature.domain.model.ratingInfo

import com.google.gson.annotations.SerializedName
import com.prafull.contestifyme.app.profileFeature.domain.model.UserRating

data class RatingResult(
    @SerializedName("contestId") val contestId: Int,
    @SerializedName("contestName") val contestName: String,
    @SerializedName("handle") val handle: String,
    @SerializedName("newRating") val newRating: Int,
    @SerializedName("oldRating") val oldRating: Int,
    @SerializedName("rank") val rank: Int,
    @SerializedName("ratingUpdateTimeSeconds") val ratingUpdateTimeSeconds: Int
) {
    fun toUserRating(): UserRating = UserRating(
        contestId = contestId,
        contestName = contestName,
        handle = handle,
        newRating = newRating,
        oldRating = oldRating,
        rank = rank,
        ratingUpdateTimeSeconds = ratingUpdateTimeSeconds
    )
}