package com.prafull.contestifyme.app.settings

import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.contestifyme.app.ai.chatScreen.AiConst
import com.prafull.contestifyme.app.friendsFeature.data.local.FriendsDB
import com.prafull.contestifyme.app.profileFeature.data.local.ProfileDatabase
import com.prafull.contestifyme.utils.managers.SharedPrefManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SettingViewModel : ViewModel(), KoinComponent {

    private val friendDb by inject<FriendsDB>()
    private val profileDb by inject<ProfileDatabase>()
    private val _loggingOut = MutableStateFlow(false)
    val loggingOut = _loggingOut
    private val _logoutSuccess = MutableStateFlow(false)
    val logoutSuccess = _logoutSuccess.asStateFlow()

    private fun deleteApiKey(context: Context) = viewModelScope.launch(Dispatchers.IO) {
        context.getSharedPreferences(AiConst.API_PREF_KEY, Context.MODE_PRIVATE).apply {
            edit {
                putBoolean(AiConst.IS_KEY_SAVED, false)
                putString(AiConst.PREF_KEY, "")
            }
        }
    }

    fun logout(context: Context) {
        _loggingOut.update { true }
        viewModelScope.launch(Dispatchers.IO) {
            deleteApiKey(context)
            profileDb.clearAllTables()
            friendDb.clearAllTables()
            SharedPrefManager(context).logout()
            _loggingOut.update { false }
            _logoutSuccess.update { true }
        }
    }
}