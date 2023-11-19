package com.example.contestifyme.features.problemsFeature.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("problems_entity")
data class ProblemsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val unique: String,
    val index: String,
    val points: Double,
    val rating: Int,
    val tags: String,
    val name: String
)
