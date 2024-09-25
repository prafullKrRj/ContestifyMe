package com.prafull.contestifyme.app.profileFeature.domain.model.submissionsInfo

import com.google.gson.annotations.SerializedName

data class SubmissionDto(
    @SerializedName("result")
    val submissions: List<Submissions>,
    @SerializedName("status")
    val status: String
)