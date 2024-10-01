package com.prafull.contestifyme.network

object CodeforcesUtil {


    fun getUserInfoUrl(handles: List<String>): String {
        return "https://codeforces.com/api/user.info?handles=${handles.joinToString(";")}"
    }

    fun getUserInfoUrl(handle: String): String {
        return "https://codeforces.com/api/user.info?handles=$handle"
    }

    fun getUserRatingUrl(handle: String): String {
        return "https://codeforces.com/api/user.rating?handle=$handle"
    }

    fun getUserSubmissionsUrl(handle: String): String {
        return "https://codeforces.com/api/user.status?handle=$handle"
    }
}