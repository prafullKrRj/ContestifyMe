package com.prafull.contestifyme.app.friendsFeature

import androidx.compose.ui.graphics.Color
import com.prafull.contestifyme.app.commons.ui.getTime
import java.time.LocalDateTime

object FriendsConstants {

    fun getUserInfo(handle: List<String>): String {
        return "https://codeforces.com/api/user.info?handles=${handle.joinToString(";")}"
    }

    fun getUserHandelInfo(handle: String): String {
        return "https://codeforces.com/api/user.info?handles=$handle"
    }

    fun getFormattedTime(inputTime: Long?): String {
        if (inputTime == null) return ""
        val time: LocalDateTime = getTime(inputTime)
        return "${time.hour}:${if (time.minute < 10) "0" + time.minute else time.minute}, ${time.dayOfMonth}-${time.monthValue}-${time.year}"
    }

    fun getRatingColor(rating: Int): Color {
        return when {
            rating < 1200 -> Color(0xFF808080)
            rating < 1400 -> Color(0xFF008000)
            rating < 1600 -> Color(0xFF03A89E)
            rating < 1900 -> Color(0xFF0000FF)
            rating < 2100 -> Color(0xFFFF8C00)
            rating < 2400 -> Color(0xFFFF0000)
            rating < 3000 -> Color(0xFF8B00FF)
            else -> Color(0xFF000000)
        }
    }
}