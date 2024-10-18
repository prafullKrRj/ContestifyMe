package com.prafull.contestifyme.utils.managers


import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)

    fun setLoggedIn(isLoggedIn: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", isLoggedIn)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    fun getLoginUserHandle(): String {
        return sharedPreferences.getString("handle", "").toString()
    }

    fun setLoginUserHandle(handle: String) {
        val editor = sharedPreferences.edit()
        editor.putString("handle", handle)
        editor.apply()
    }

    fun logout() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}