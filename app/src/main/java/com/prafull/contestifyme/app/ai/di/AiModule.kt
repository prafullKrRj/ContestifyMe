package com.prafull.contestifyme.app.ai.di

import android.content.Context
import androidx.room.Room
import com.prafull.contestifyme.app.ai.aiSettings.SettingsViewModel
import com.prafull.contestifyme.app.ai.chatScreen.AiConst
import com.prafull.contestifyme.app.ai.chatScreen.ChatViewModel
import com.prafull.contestifyme.app.ai.data.AIDatabase
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
    single<AIDatabase> {
        Room.databaseBuilder(androidContext(), AIDatabase::class.java, AiConst.DB_NAME)
            .build()
    }
    single {
        get<AIDatabase>().chatDao()
    }
    viewModel<SettingsViewModel> {
        SettingsViewModel()
    }
}

suspend fun fetchApiKey(context: Context): String {
    val sharedPref = context.getSharedPreferences(AiConst.API_PREF_KEY, Context.MODE_PRIVATE)
    return sharedPref.getString(AiConst.PREF_KEY, "") ?: ""
}

data class ApiKey(
    val apiKey: String
)