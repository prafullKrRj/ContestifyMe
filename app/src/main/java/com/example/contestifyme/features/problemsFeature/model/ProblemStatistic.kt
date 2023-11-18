package com.example.contestifyme.features.problemsFeature.model

import com.google.gson.annotations.SerializedName

data class ProblemStatistic(
    @SerializedName("contestId") val contestId: Int,
    @SerializedName("index") val index: String,
    @SerializedName("solvedCount") val solvedCount: Int
)