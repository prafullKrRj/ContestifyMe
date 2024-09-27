package com.prafull.contestifyme.app.profileFeature.domain.model

data class UserRating(
    val contestId: Int = 0,
    val contestName: String = "",
    val handle: String = "",
    val newRating: Int = 0,
    val oldRating: Int = 0,
    val rank: Int = 0,
    val ratingUpdateTimeSeconds: Int = 0
)
