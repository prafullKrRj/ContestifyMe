package com.prafull.contestifyme.network.model.userrating

import com.google.gson.annotations.SerializedName

data class RatingDto(
    @SerializedName("result")
    val result: List<RatingResult>,
    val status: String
) {
    fun toUserRatings() = result.map { it.toUserRating() }

    fun toUserRatingEntity() = result.map { it.toUserRatingEntity() }
}