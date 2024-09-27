package com.prafull.contestifyme.app.profileFeature.domain.model

data class UserSubmissions(
    val id: Int = 0,
    val name: String = "",
    val verdict: String = "",
    val time: Int = 0,
    val contestId: Int = 0,
    val index: String = "",
    val rating: Int = 0,
    val tags: List<String> = emptyList()
)
