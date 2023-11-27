package com.example.contestifyme.features.friendsFeature

object FriendsConstants {

    fun getUserInfo(handle: List<String>): String {
        return "https://codeforces.com/api/user.info?handles=${handle.joinToString(";")}/"
    }
}