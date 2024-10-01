package com.prafull.contestifyme.app.problemsFeature.domain.model.acmsguru

import com.google.gson.annotations.SerializedName

data class ProblemStatistic(
    @SerializedName("index")
    val index: String,
    @SerializedName("solvedCount")
    val solvedCount: Int
)