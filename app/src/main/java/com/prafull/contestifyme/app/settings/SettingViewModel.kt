package com.prafull.contestifyme.app.settings

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.contestifyme.app.ai.chatScreen.AiConst
import com.prafull.contestifyme.app.friendsFeature.domain.FriendRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SettingViewModel : ViewModel(), KoinComponent {

    private val friendsRepo by inject<FriendRepo>()
    fun deleteApiKey(context: Context) = viewModelScope.launch(Dispatchers.IO) {
        context.getSharedPreferences(AiConst.API_PREF_KEY, Context.MODE_PRIVATE).apply {
            edit {
                putBoolean(AiConst.IS_KEY_SAVED, false)
                putString(AiConst.PREF_KEY, "")
            }
            Log.d("DeleteApi", "true")
        }
    }

    fun deleteAllFriends() {
        viewModelScope.launch(Dispatchers.IO) {
            friendsRepo.deleteAll()
        }
    }
}