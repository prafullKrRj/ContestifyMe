package com.prafull.contestifyme.features.contestsFeature.domain.model

import com.google.gson.annotations.SerializedName

data class ContestsDto(
    @SerializedName("result")
    val result: List<Result>,
    @SerializedName("status")
    val status: String
)