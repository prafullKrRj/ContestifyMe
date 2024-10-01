package com.prafull.contestifyme.app.ai

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.prafull.contestifyme.app.ai.chatScreen.AiConst
import com.prafull.contestifyme.app.ai.chatScreen.ChatScreen
import com.prafull.contestifyme.app.ai.enrollToAI.EnrollingScreen
import org.koin.androidx.compose.getViewModel


enum class AiScreens {
    CHAT_SCREEN, ENROLLMENT_SCREEN
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiScreen(navController: NavController) {
    val context = LocalContext.current
    var currScreen by rememberSaveable {
        mutableStateOf(
            if (context.getSharedPreferences(AiConst.API_PREF_KEY, Context.MODE_PRIVATE)
                    .getBoolean("isKeySaved", false)
            ) {
                AiScreens.CHAT_SCREEN
            } else {
                AiScreens.ENROLLMENT_SCREEN
            }
        )
    }
    Column(
        Modifier
            .fillMaxSize()
    ) {
        when (currScreen) {
            AiScreens.CHAT_SCREEN -> {
                ChatScreen(getViewModel())
            }

            AiScreens.ENROLLMENT_SCREEN -> {
                EnrollingScreen(getViewModel()) {
                    currScreen = AiScreens.CHAT_SCREEN
                }
            }
        }
    }
}
