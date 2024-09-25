package com.prafull.contestifyme.app.problemsFeature.domain.model

import com.google.gson.annotations.SerializedName

data class ProblemsDto(
    @SerializedName("result")
    val result: Result,
    @SerializedName("status")
    val status: String
)