package com.prafull.contestifyme.app.commons

import com.prafull.contestifyme.network.model.UserRating
import com.prafull.contestifyme.network.model.UserSubmissions
import com.prafull.contestifyme.network.model.userinfo.UserResult

data class UserData(
    val handle: String,
    val usersInfo: UserResult,
    val userSubmissions: List<UserSubmissions>,
    val userRating: List<UserRating>
) {
    fun toUserRatingEntities() = userRating.map { it.toUserRatingEntity() }
    fun toUserSubmissionEntities() = userSubmissions.map { it.toUserSubmissionEntity() }
}