package com.prafull.contestifyme.app.contestsFeature.domain.model

import com.google.gson.annotations.SerializedName
import com.prafull.contestifyme.app.contestsFeature.data.local.ContestsEntity

data class ContestsDto(
    @SerializedName("result")
    val contestsResult: List<ContestsResult>,
    @SerializedName("status")
    val status: String
) {
    fun toContestEntities(): List<ContestsEntity> {
        return contestsResult.map { it.toContestEntity() }
    }
}