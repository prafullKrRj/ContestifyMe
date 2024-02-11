package com.prafull.contestifyme.features.profileFeature.domain.model.ratingInfo

import com.google.gson.annotations.SerializedName

data class RatingResult(
    @SerializedName("contestId") val contestId: Int,
    @SerializedName("contestName") val contestName: String,
    @SerializedName("handle") val handle: String,
    @SerializedName("newRating") val newRating: Int,
    @SerializedName("oldRating") val oldRating: Int,
    @SerializedName("rank") val rank: Int,
    @SerializedName("ratingUpdateTimeSeconds") val ratingUpdateTimeSeconds: Int
)