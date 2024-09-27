package com.prafull.contestifyme.app.profileFeature.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.prafull.contestifyme.app.commons.UserData
import com.prafull.contestifyme.app.profileFeature.domain.model.UserRating
import com.prafull.contestifyme.app.profileFeature.domain.model.UserSubmissions
import com.prafull.contestifyme.onboard.model.UserResult

@Entity(tableName = "user_info")
data class UserInfoEntity(
    @PrimaryKey val id: Int = 1,
    val handle: String,
    val avatar: String?,
    val contribution: Int?,
    val country: String?,
    val name: String?,
    val friendOfCount: Int?,
    val lastOnlineTimeSeconds: Int?,
    val maxRank: String?,
    val maxRating: Int?,
    val organization: String?,
    val rank: String?,
    val rating: Int?,
    val registrationTimeSeconds: Int?,
    val titlePhoto: String?,
    val ratingInfo: List<UserRating>,
    val subMissionInfo: List<UserSubmissions>
) {
    fun toUserData() = UserData(
        handle = handle,
        usersInfo = UserResult(
            avatar = avatar,
            contribution = contribution,
            country = country,
            firstName = name,
            friendOfCount = friendOfCount,
            handle = handle,
            lastOnlineTimeSeconds = lastOnlineTimeSeconds,
            maxRank = maxRank,
            maxRating = maxRating,
            organization = organization,
            rank = rank,
            rating = rating,
            registrationTimeSeconds = registrationTimeSeconds,
            titlePhoto = titlePhoto
        ),
        userSubmissions = subMissionInfo,
        userRating = ratingInfo
    )
}

class ProfileTypeConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromUserRatingList(list: List<UserRating>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromUserStatusList(list: List<UserSubmissions>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toUserRatingList(string: String): List<UserRating> {
        val listType = object : TypeToken<List<UserRating>>() {}.type
        return gson.fromJson(string, listType)
    }

    @TypeConverter
    fun toUserStatusList(string: String): List<UserSubmissions> {
        return gson.fromJson(string, object : TypeToken<List<UserSubmissions>>() {}.type)
    }
}