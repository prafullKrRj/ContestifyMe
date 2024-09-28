package com.prafull.contestifyme.app.contestsFeature.domain.model

import com.google.gson.annotations.SerializedName

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
)