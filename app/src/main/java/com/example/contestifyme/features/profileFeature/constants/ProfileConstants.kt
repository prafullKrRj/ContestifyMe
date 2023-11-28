package com.example.contestifyme.features.profileFeature.constants

import androidx.compose.ui.graphics.Color
import com.example.contestifyme.ui.theme.md_theme_dark_tertiaryContainer

object ProfileConstants {

    fun getUserRating(handle: String): String {
        return "https://codeforces.com/api/user.rating?handle=$handle"
    }
    fun getUserInfo(handle: String): String {
        return "https://codeforces.com/api/user.info?handles=$handle"
    }
    fun getUserStatus(handle: String): String {
        return "https://codeforces.com/api/user.status?handle=$handle"
    }
    fun getUserStatus(handle: List<String>): String {
        return "https://codeforces.com/api/user.status?handle=${handle.joinToString(";")}"
    }

    val verdictsColors = mapOf(
        "FAILED" to (Color(0xFFFF0000) to Color(0xFFFFFFFF)), // Red background with white text
        "ACCEPTED" to (md_theme_dark_tertiaryContainer to Color.White), // Green background with black text
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
    val solvedByTagsColor = mapOf(
        "2-sat" to Color(0xFF0000FF),
        "binary search" to Color(0xFFFF0000),
        "bitmasks" to Color(0xFF00FF00),
        "brute force" to Color(0xFFFFFF00),
        "chinese remainder theorem" to Color(0xFF00FFFF),
        "combinatorics" to Color(0xFFFF00FF),
        "constructive algorithms" to Color(0xFF800080),
        "data structures" to Color(0xFF008080),
        "dfs and similar" to Color(0xFF000080),
        "divide and conquer" to Color(0xFF800000),
        "dp" to Color(0xFF008000),
        "dsu" to Color(0xFF808000),
        "expression parsing" to Color(0xFF808080),
        "fft" to Color(0xFF8000FF),
        "flows" to Color(0xFF00FFFF),
        "games" to Color(0xFFFF00FF),
        "geometry" to Color(0xFF00FF00),
        "graph matchings" to Color(0xFF0000FF),
        "graphs" to Color(0xFFFF0000),
        "greedy" to Color(0xFF00FF80),
        "hashing" to Color(0xFF00FFFF),
        "implementation" to Color(0xFF0080FF),
        "interactive" to Color(0xFF8000FF),
        "math" to Color(0xFF804040),
        "matrices" to Color(0xFF00FF80),
        "meet-in-the-middle" to Color(0xFF8080FF),
        "number theory" to Color(0xFFFF8080),
        "probabilities" to Color(0xFF808000),
        "schedules" to Color(0xFF804080),
        "shortest paths" to Color(0xFF800040),
        "sortings" to Color(0xFFFF8040),
        "string suffix structures" to Color(0xFF408080),
        "strings" to Color(0xFF404080),
        "ternary search" to Color(0xFF804000),
        "trees" to Color(0xFF400040),
        "two pointers" to Color(0xFFFF8040)
    )
}