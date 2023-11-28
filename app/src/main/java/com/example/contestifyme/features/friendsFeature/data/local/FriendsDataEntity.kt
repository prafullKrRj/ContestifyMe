package com.example.contestifyme.features.friendsFeature.data.local

import androidx.room.Entity
import androidx.room.TypeConverter
import com.example.contestifyme.features.profileFeature.model.UserRating
import com.example.contestifyme.features.profileFeature.model.UserSubmissions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "friends_data", primaryKeys = ["handle"])
data class FriendsDataEntity (
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
    var ratingInfo: List<UserRating> = emptyList(),
    var subMissionInfo: List<UserSubmissions> = emptyList()
)
class FriendsTypeConverters {
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