package com.prafull.contestifyme.network.model.userstatus

import com.google.gson.annotations.SerializedName

data class SubmissionDto(
    @SerializedName("result")
    val submissions: List<Submissions>,
    @SerializedName("status")
    val status: String
) {
    fun toUserStatus() = submissions.map { it.toUserSubmission() }
}