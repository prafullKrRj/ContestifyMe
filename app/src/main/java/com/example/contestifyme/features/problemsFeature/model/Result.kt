package com.example.contestifyme.features.problemsFeature.model

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("problemStatistics") val problemStatistics: List<ProblemStatistic>,
    @SerializedName("problems") val problems: List<Problem>
)