package com.prafull.contestifyme.app.commons

import androidx.compose.ui.graphics.Color

object Utils {
    fun getRankColor(rank: String?): Color {
        return when (rank) {
            "tourist" -> Color.Black
            "legendary grandmaster" -> Color(0xFFAA0000)     // Dark Red
            "international grandmaster" -> Color(0xFFFF3333) // Darker Red
            "grandmaster" -> Color(0xFFFF7777)              // Red
            "international master" -> Color(0xFFFFBB55)     // Orange with slight variation
            "master" -> Color(0xFFFFCC88)                   // Orange
            "candidate master" -> Color(0xFFFF88FF)         // Violet
            "expert" -> Color(0xFFAAAAFF)                   // Blue
            "specialist" -> Color(0xFF77DDBB)               // Cyan
            "pupil" -> Color(0xFF77FF77)                    // Green
            "newbie" -> Color(0xFFCCCCCC)                   // Gray
            else -> Color.Red                               // Default Red for unspecified ranks
        }
    }
}