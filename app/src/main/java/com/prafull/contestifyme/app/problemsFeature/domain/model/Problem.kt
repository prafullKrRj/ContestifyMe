package com.prafull.contestifyme.app.problemsFeature.domain.model

import com.google.gson.annotations.SerializedName
import com.prafull.contestifyme.app.problemsFeature.data.local.entities.ProblemsEntity

data class Problem(
    @SerializedName("contestId") val contestId: Int,
    @SerializedName("index") val index: String,
    @SerializedName("name") val name: String,
    @SerializedName("points") val points: Double,
    @SerializedName("rating") val rating: Int,
    @SerializedName("tags") val tags: List<String>,
    @SerializedName("type") val type: String
) {
    fun toProblemEntity() = ProblemsEntity(
        unique = "$contestId|$index|$rating|$name",
        index = index,
        points = points,
        rating = rating,
        tags = tags,
        name = name
    )
}