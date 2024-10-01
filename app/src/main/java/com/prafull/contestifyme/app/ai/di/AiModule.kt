package com.prafull.contestifyme.app.ai.di

import android.content.Context
import com.prafull.contestifyme.app.ai.chatScreen.AiConst
import com.prafull.contestifyme.app.ai.chatScreen.ChatViewModel
import com.prafull.contestifyme.app.ai.enrollToAI.ApiKeyViewModel
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val aiModule = module {
    viewModel {
        ChatViewModel(get())
    }
    single<ApiKey> {
        val apiKey = runBlocking { fetchApiKey(androidContext()) }
        ApiKey(apiKey)
    }
    viewModel {
        ApiKeyViewModel(androidContext())
    }
}

suspend fun fetchApiKey(context: Context): String {
    val sharedPref = context.getSharedPreferences(AiConst.API_PREF_KEY, Context.MODE_PRIVATE)
    return sharedPref.getString(AiConst.PREF_KEY, "") ?: ""
}

data class ApiKey(
    val apiKey: String
)