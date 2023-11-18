package com.example.contestifyme.features.profileFeature.constants

object Constants {

    fun getUserRating(handle: String): String {
        return "https://codeforces.com/api/user.rating?handle=$handle"
    }
    fun getUserInfo(handle: String): String {
        return "https://codeforces.com/api/user.info?handles=$handle"
    }
    fun getUserStatus(handle: String): String {
        return "https://codeforces.com/api/user.status?handle=$handle"
    }
}