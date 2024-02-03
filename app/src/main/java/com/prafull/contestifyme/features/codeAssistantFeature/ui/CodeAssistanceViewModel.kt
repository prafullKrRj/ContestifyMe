package com.prafull.contestifyme.features.codeAssistantFeature.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import kotlinx.coroutines.launch


data class ChatUiState(
    val chat: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
class CodeAssistanceViewModel() : ViewModel() {

    private var openAI: OpenAI = OpenAI("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", organization = "org-Ds98Nj1dZrnyH6KmJsBxVFZh")
    private val chat = mutableStateListOf<ChatMessage>()
    val chatMessages: List<ChatMessage>
        get() = chat

    fun chatUpdate(message: String) = viewModelScope.launch {
        chat.add(
            ChatMessage(
                role = ChatRole.User,
                content = message
            )
        )
        val chatCompletionRequest = ChatCompletionRequest(
            model = ModelId("gpt-3.5-turbo-1106"),
            messages = chat.toList(),
            maxTokens = 400
        )
        val completion: ChatCompletion = openAI.chatCompletion(chatCompletionRequest)
        chat.add(
            completion.choices[0].message
        )
        Log.d("prafull", "chatUpdate: $chatMessages")
    }
}