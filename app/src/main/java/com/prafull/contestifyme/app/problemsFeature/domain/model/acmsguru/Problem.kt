package com.prafull.contestifyme.app.problemsFeature.domain.model.acmsguru

import com.google.gson.annotations.SerializedName
import com.prafull.contestifyme.app.problemsFeature.data.local.entities.ProblemsEntity

data class Problem(
    @SerializedName("index")
    val index: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("problemsetName")
    val problemsetName: String,
    @SerializedName("tags")
    val tags: List<Any>,
    @SerializedName("type")
    val type: String
) {
    fun toEntity(): ProblemsEntity {
        return ProblemsEntity(
            unique = "$index-$name",
            index = index,
            points = 0.0,
            rating = 0,
            tags = tags.map { it.toString() },
            name = name
        )
    }
}