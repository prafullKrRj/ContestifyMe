package com.prafull.contestifyme.app.ai.enrollToAI

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.prafull.contestifyme.app.ai.chatScreen.AiConst
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

// ViewModel
class ApiKeyViewModel(
    context: Context
) : ViewModel(), KoinComponent {
    private val pref = context.getSharedPreferences(AiConst.API_PREF_KEY, Context.MODE_PRIVATE)

    private val _keyAdded = MutableStateFlow(false)
    val keyAdded = _keyAdded.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun saveApiKey(apiKey: String, context: Context) {
        _loading.update { true }
        viewModelScope.launch {
            verifyApiKey(apiKey).collectLatest {
                Toast.makeText(
                    context,
                    if (it) "API Key saved successfully" else "Invalid API Key",
                    Toast.LENGTH_SHORT
                ).show()
                if (!it) {
                    _loading.update { false }
                    return@collectLatest
                }
            }
            pref.apply {
                edit().putBoolean("isKeySaved", true).apply()
                edit().putString(AiConst.PREF_KEY, apiKey).apply()
            }
            _keyAdded.update { true }
            _loading.update {
                false
            }
        }
    }
}

fun verifyApiKey(key: String): Flow<Boolean> = flow {
    try {
        val content = GenerativeModel(
            apiKey = key, modelName = "gemini-1.5-flash"
        ).generateContent("Say Hi")
        emit(true)
    } catch (e: Exception) {
        emit(false)
    }
}