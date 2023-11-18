package com.example.contestifyme.ui.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AppUser(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val handle: String = "",
    val boolean: Boolean = false
)
