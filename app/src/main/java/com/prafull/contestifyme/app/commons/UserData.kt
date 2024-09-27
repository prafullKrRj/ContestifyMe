package com.prafull.contestifyme.app.commons

import com.prafull.contestifyme.app.friendsFeature.domain.FriendData
import com.prafull.contestifyme.app.profileFeature.domain.model.UserRating
import com.prafull.contestifyme.app.profileFeature.domain.model.UserSubmissions
import com.prafull.contestifyme.onboard.model.UserResult

data class UserData(
    val handle: String,
    val usersInfo: UserResult,
    val userSubmissions: List<UserSubmissions>,
    val userRating: List<UserRating>
) {
    fun toFriendData(): FriendData {
        return FriendData(
            handle = handle,
            usersInfo = usersInfo,
            userSubmissions = userSubmissions,
            userRating = userRating
        )
    }
}