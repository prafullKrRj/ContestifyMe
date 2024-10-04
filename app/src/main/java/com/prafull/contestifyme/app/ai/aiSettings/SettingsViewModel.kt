package com.prafull.contestifyme.app.ai.aiSettings

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.contestifyme.app.ai.chatScreen.AiConst
import com.prafull.contestifyme.app.ai.data.ChatDao
import com.prafull.contestifyme.app.ai.enrollToAI.verifyApiKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class SettingsViewModel : ViewModel(), KoinComponent {

    private val _keyAdded = MutableStateFlow(true)
    val keyAdded: StateFlow<Boolean> = _keyAdded

    fun deleteApiKey(context: Context) {
        viewModelScope.launch {
            context.getSharedPreferences(AiConst.API_PREF_KEY, Context.MODE_PRIVATE).apply {
                edit().putBoolean(AiConst.IS_KEY_SAVED, false).apply()
                edit().putString(AiConst.PREF_KEY, "").apply()
            }
            _keyAdded.update { false }
        }
    }

    fun deleteHistory(context: Context) {
        viewModelScope.launch() {
            dao.deleteAll()
            Toast.makeText(
                context, "History deleted successfully", Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun changeApiKey(apiKey: String, context: Context) {
        viewModelScope.launch {
            _loading.update { true }
            verifyApiKey(apiKey).collectLatest {
                if (!it) {
                    _loading.update { false }
                    Toast.makeText(
                        context, "Invalid API Key", Toast.LENGTH_SHORT
                    ).show()
                    return@collectLatest
                }
                context.getSharedPreferences(AiConst.API_PREF_KEY, Context.MODE_PRIVATE).apply {
                    edit().putBoolean(AiConst.IS_KEY_SAVED, true).apply()
                    edit().putString(AiConst.PREF_KEY, apiKey).apply()
                }
                _loading.update { false }
                Toast.makeText(
                    context, "API Key added successfully", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading
    private val dao: ChatDao by inject()

}