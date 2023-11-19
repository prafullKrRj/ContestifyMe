package com.example.contestifyme.features.problemsFeature.model

import com.google.gson.annotations.SerializedName

data class ProblemsDto(
    @SerializedName("result")
    val result: Result,
    @SerializedName("status")
    val status: String
) {

}