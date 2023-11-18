package com.example.contestifyme.constants

object Constants {
    const val BASE_URL = "https://codeforces.com/api/"
    const val API_KEY = "ecca4351ee38c1ceb99c3f5c84066c25368c49e4"
    const val SECRET_KEY = "80b0ae8938f4aa179acf82ceb2383f2dbf232f33"

    fun getUserStart(handle: String) : String {
        return "https://codeforces.com/api/user.info?handles=$handle"
    }
}