package com.prafull.contestifyme.features.profileFeature.domain.model.submissionsInfo

import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("contestId") val contestId: Int,
    @SerializedName("ghost") val ghost: Boolean,
    @SerializedName("members") val members: List<Member>,
    @SerializedName("participantType") val participantType: String,
    @SerializedName("startTimeSeconds") val startTimeSeconds: Int
)