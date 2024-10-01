package com.prafull.contestifyme.network.model

import com.prafull.contestifyme.app.profileFeature.data.local.entities.UserSubmissionsEntity


data class UserSubmissions(
    val id: Int = 0,
    val name: String = "",
    val verdict: String = "",
    val time: Int = 0,
    val contestId: Int = 0,
    val index: String = "",
    val programmingLanguage: String = "",
    val rating: Int = 0,
    val tags: List<String> = emptyList()
) {
    fun toUserSubmissionEntity(): UserSubmissionsEntity {
        return UserSubmissionsEntity(
            name = name,
            id = id,
            verdict = verdict,
            programmingLanguage = programmingLanguage,
            time = time,
            contestId = contestId,
            index = index,
            rating = rating,
            tags = tags
        )
    }
}