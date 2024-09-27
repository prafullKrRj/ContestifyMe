package com.prafull.contestifyme.app.profileFeature.domain.model.ratingInfo

import com.google.gson.annotations.SerializedName

data class RatingDto(
    @SerializedName("result")
    val result: List<RatingResult>,
    val status: String
) {
    fun toUserRatings() = result.map { it.toUserRating() }
}