package com.prafull.contestifyme.app.problemsFeature.data.local.entities

import androidx.room.Entity
import androidx.room.TypeConverter

@Entity("problems_entity", primaryKeys = ["unique"])
data class ProblemsEntity(
    val unique: String,
    val index: String,
    val points: Double,
    val rating: Int,
    val tags: List<String>,
    val name: String
)

class ProblemsTypeConverter {

    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toStringList(string: String): List<String> {
        return string.split(",")
    }
}