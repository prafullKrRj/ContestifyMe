package com.example.contestifyme.features.profileFeature.model.submissionsInfo

import com.google.gson.annotations.SerializedName

data class SubmissionDto(
    @SerializedName("result")
    val result: List<Result>,
    @SerializedName("status")
    val status: String
)