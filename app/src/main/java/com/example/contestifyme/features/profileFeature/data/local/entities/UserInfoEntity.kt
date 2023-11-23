package com.example.contestifyme.features.profileFeature.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.contestifyme.features.profileFeature.model.UserRating
import com.example.contestifyme.features.profileFeature.model.UserStatus
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "user_info")
data class UserInfoEntity(
    @PrimaryKey
    val id: Int = 1,
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
    val statusInfo: List<UserStatus>
)

class ProfileTypeConverters {
    private val gson = Gson()
    @TypeConverter
    fun fromUserRatingList(list: List<UserRating>): String {
        return gson.toJson(list)
    }
    @TypeConverter
    fun fromUserStatusList(list: List<UserStatus>): String {
        return gson.toJson(list)
    }
    @TypeConverter
    fun toUserRatingList(string: String): List<UserRating> {
        val listType = object : TypeToken<List<UserRating>>() {}.type
        return gson.fromJson(string, listType)
    }
    @TypeConverter
    fun toUserStatusList(string: String): List<UserStatus> {
        return gson.fromJson(string, object : TypeToken<List<UserStatus>>() {}.type)
    }
}