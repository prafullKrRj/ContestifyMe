package com.prafull.contestifyme.app.contestsFeature.domain.model

import com.google.gson.annotations.SerializedName

data class ContestsDto(
    @SerializedName("result")
    val contestsResult: List<ContestsResult>,
    @SerializedName("status")
    val status: String
)