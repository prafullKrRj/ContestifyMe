package com.prafull.contestifyme.app.contestsFeature.domain.model

import com.google.gson.annotations.SerializedName
import com.prafull.contestifyme.app.contestsFeature.data.local.ContestsEntity

data class ContestsResult(
    @SerializedName("durationSeconds")
    val durationSeconds: Int,

    @SerializedName("frozen")
    val frozen: Boolean,

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("phase")
    val phase: String,

    @SerializedName("relativeTimeSeconds")
    val relativeTimeSeconds: Int,

    @SerializedName("startTimeSeconds")
    val startTimeSeconds: Int,

    @SerializedName("type")
    val type: String
) {
    fun toContestEntity() = ContestsEntity(
        id, durationSeconds, frozen, name, phase, relativeTimeSeconds, startTimeSeconds, type
    )
}