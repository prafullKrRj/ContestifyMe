package com.example.contestifyme.features.profileFeature.constants

import androidx.compose.ui.graphics.Color
import com.example.contestifyme.ui.theme.CustomColor2

object ProfileConstants {

    fun getUserRating(handle: String): String {
        return "https://codeforces.com/api/user.rating?handle=$handle"
    }
    fun getUserInfo(handle: String): String {
        return "https://codeforces.com/api/user.info?handles=$handle"
    }
    fun getUserStatus(handle: String, start: Int, end: Int): String {
        return "https://codeforces.com/api/user.status?handle=$handle&from=$start&count=$end"
    }



    val colors = mapOf(
        "FAILED" to (Color(0xFFFF0000) to Color(0xFFFFFFFF)), // Red background with white text
        "OK" to (Color(0xFF00FF00) to Color(0xFF000000)), // Green background with black text
        "PARTIAL" to (Color(0xFFFFFF00) to Color(0xFF000000)), // Yellow background with black text
        "COMPILATION_ERROR" to (Color(0xFFFFA500) to Color(0xFF000000)), // Orange background with black text
        "RUNTIME_ERROR" to (Color(0xFF800080) to Color(0xFFFFFFFF)), // Purple background with white text
        "WRONG_ANSWER" to (Color(0xFFFF6347) to Color(0xFFFFFFFF)), // Tomato background with white text
        "PRESENTATION_ERROR" to (Color(0xFFADD8E6) to Color(0xFF000000)), // Light Blue background with black text
        "TIME_LIMIT_EXCEEDED" to (Color(0xFFFFA07A) to Color(0xFF000000)), // Light Salmon background with black text
        "MEMORY_LIMIT_EXCEEDED" to (Color(0xFF20B2AA) to Color(0xFFFFFFFF)), // Light Sea Green background with white text
        "IDLENESS_LIMIT_EXCEEDED" to (Color(0xFFD3D3D3) to Color(0xFF000000)), // Light Gray background with black text
        "SECURITY_VIOLATED" to (Color(0xFF8B0000) to Color(0xFFFFFFFF)), // Dark Red background with white text
        "CRASHED" to (Color(0xFFBDB76B) to Color(0xFF000000)), // Dark Khaki background with black text
        "INPUT_PREPARATION_CRASHED" to (Color(0xFF2F4F4F) to Color(0xFFFFFFFF)), // Dark Slate Gray background with white text
        "CHALLENGED" to (Color(0xFFFFD700) to Color(0xFF000000)), // Gold background with black text
        "SKIPPED" to (Color(0xFF808080) to Color(0xFFFFFFFF)), // Gray background with white text
        "TESTING" to (Color(0xFF00CED1) to Color(0xFF000000)), // Dark Turquoise background with black text
        "REJECTED" to (Color(0xFFDC143C) to Color(0xFFFFFFFF)) // Crimson background with white text
    )
}