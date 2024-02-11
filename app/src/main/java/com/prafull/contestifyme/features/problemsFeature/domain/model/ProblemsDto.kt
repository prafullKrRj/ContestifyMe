package com.prafull.contestifyme.features.problemsFeature.domain.model

import com.google.gson.annotations.SerializedName

data class ProblemsDto(
    @SerializedName("result")
    val result: Result,
    @SerializedName("status")
    val status: String
) {

}