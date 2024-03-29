package com.prafull.contestifyme.features.problemsFeature.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity("problems_entity")
data class ProblemsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
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