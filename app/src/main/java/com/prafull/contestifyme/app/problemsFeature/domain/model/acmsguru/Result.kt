package com.prafull.contestifyme.app.problemsFeature.domain.model.acmsguru

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("problem_statistics") val problemStatistics: List<ProblemStatistic>,
    @SerializedName("problems") val problems: List<Problem>
)