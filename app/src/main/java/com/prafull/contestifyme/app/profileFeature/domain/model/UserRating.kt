package com.prafull.contestifyme.app.profileFeature.domain.model

data class UserRating(
    val contestId: Int,
    val contestName: String,
    val handle: String,
    val newRating: Int,
    val oldRating: Int,
    val rank: Int,
    val ratingUpdateTimeSeconds: Int
)
