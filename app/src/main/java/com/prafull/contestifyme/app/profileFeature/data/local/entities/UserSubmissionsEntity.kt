package com.prafull.contestifyme.app.profileFeature.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.prafull.contestifyme.network.model.UserSubmissions

@Entity(tableName = "user_submissions")
data class UserSubmissionsEntity(
    val handle: String = "",
    @PrimaryKey
    val id: Int,
    val name: String = "",
    val verdict: String = "",
    val time: Int = 0,
    val contestId: Int = 0,
    val index: String = "",
    val programmingLanguage: String = "",
    val rating: Int = 0,
    val tags: List<String> = emptyList()
) {
    fun toUserSubmissions(): UserSubmissions = UserSubmissions(
        id = id,
        name = name,
        verdict = verdict,
        time = time,
        contestId = contestId,
        index = index,
        programmingLanguage = programmingLanguage,
        rating = rating,
        tags = tags
    )
}

class ProfileTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String): List<String> {
        return gson.fromJson(value, Array<String>::class.java).toList()
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return gson.toJson(list)
    }
}