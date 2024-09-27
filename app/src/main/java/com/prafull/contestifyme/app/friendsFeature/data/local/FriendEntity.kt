package com.prafull.contestifyme.app.friendsFeature.data.local

import androidx.room.Entity
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.prafull.contestifyme.app.friendsFeature.domain.FriendData
import com.prafull.contestifyme.app.profileFeature.domain.model.UserRating
import com.prafull.contestifyme.app.profileFeature.domain.model.UserSubmissions
import com.prafull.contestifyme.onboard.model.UserResult


@Entity(primaryKeys = ["handle"])
data class FriendEntity(
    val handle: String,
    val rating: Int,
    val friendInfo: UserResult,
    val friendSubmissions: List<UserSubmissions> = emptyList(),
    val friendRating: List<UserRating> = emptyList()
) {
    fun toFriendData() = FriendData(
        handle = handle,
        usersInfo = friendInfo,
        userSubmissions = friendSubmissions,
        userRating = friendRating
    )
}

class FriendConverter {
    @TypeConverter
    fun fromUserResult(userResult: UserResult?): String? {
        return if (userResult == null) null else Gson().toJson(userResult)
    }

    @TypeConverter
    fun toUserResult(userResultJson: String?): UserResult? {
        return if (userResultJson == null) null else Gson().fromJson(
            userResultJson, UserResult::class.java
        )
    }

    @TypeConverter
    fun fromUserSubmissionsList(value: List<UserSubmissions>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toUserSubmissionsList(value: String): List<UserSubmissions> {
        val listType = object : TypeToken<List<UserSubmissions>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromUserRatingList(value: List<UserRating>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toUserRatingList(value: String): List<UserRating> {
        val listType = object : TypeToken<List<UserRating>>() {}.type
        return Gson().fromJson(value, listType)
    }
}
