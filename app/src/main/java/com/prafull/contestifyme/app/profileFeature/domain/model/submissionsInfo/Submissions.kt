package com.prafull.contestifyme.app.profileFeature.domain.model.submissionsInfo

import com.google.gson.annotations.SerializedName

data class Submissions(
    @SerializedName("author") val author: Author,
    @SerializedName("contestId") val contestId: Int,
    @SerializedName("creationTimeSeconds") val creationTimeSeconds: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("memoryConsumedBytes") val memoryConsumedBytes: Int,
    @SerializedName("passedTestCount") val passedTestCount: Int,
    @SerializedName("problem") val problem: Problem,
    @SerializedName("programmingLanguage") val programmingLanguage: String,
    @SerializedName("relativeTimeSeconds") val relativeTimeSeconds: Int,
    @SerializedName("testset") val testset: String,
    @SerializedName("timeConsumedMillis") val timeConsumedMillis: Int,
    @SerializedName("verdict") val verdict: String
)