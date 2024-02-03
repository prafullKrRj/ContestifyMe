package com.prafull.contestifyme.features.profileFeature.model.ratingInfo

import com.google.gson.annotations.SerializedName

data class RatingDto(
    @SerializedName("result")
    val result: List<RatingResult>,
    val status: String
) {

}