package com.prafull.contestifyme.app.friendsFeature.domain

import com.prafull.contestifyme.app.commons.UserData
import com.prafull.contestifyme.app.profileFeature.domain.model.UserRating
import com.prafull.contestifyme.app.profileFeature.domain.model.UserSubmissions
import com.prafull.contestifyme.onboard.model.UserResult


data class FriendData(
    val handle: String,
    val usersInfo: UserResult,
    val userSubmissions: List<UserSubmissions>,
    val userRating: List<UserRating>
) {
    fun toUserData() = UserData(
        handle, usersInfo, userSubmissions, userRating
    )
}