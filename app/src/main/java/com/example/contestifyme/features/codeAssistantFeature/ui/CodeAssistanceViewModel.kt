package com.example.contestifyme.features.codeAssistantFeature.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import kotlinx.coroutines.launch
import java.lang.Exception


data class ChatUiState(
    val chat: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
class CodeAssistanceViewModel() : ViewModel() {

    private val _uiState: MutableState<ChatUiState> = mutableStateOf(ChatUiState())

    val uiState: ChatUiState = _uiState.value

    private var openAI: OpenAI = OpenAI("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")
    private val chat = mutableStateListOf<ChatMessage>()
    init {
       /* chat.add(
            ChatMessage(
                role = ChatRole.User,
                content = "Keep the next chat related to programming and related fields only and by chance i ask any question which you think is out of programming domain just response I can' answer",

            )
        )*/
    }
    val chatMessages: List<ChatMessage>
        get() = chat

    fun chatUpdate(message: String) = viewModelScope.launch {
        _uiState.value.copy(
            isLoading = true
        )
        try {
            chat.add(
                ChatMessage(
                    role = ChatRole.User,
                    content = message
                )
            )
            _uiState.value.copy(
                chat = chatMessages
            )
            val chatCompletionRequest = ChatCompletionRequest(
                model = ModelId("gpt-3.5-turbo-1106"),
                messages = chat,
                maxTokens = 400
            )
            val completion: ChatCompletion = openAI.chatCompletion(chatCompletionRequest)
            chat.add(
                completion.choices[0].message
            )
            _uiState.value.copy(
                chat = chatMessages
            )
        } catch (e: Exception) {
            _uiState.value.copy(
                error = e.message
            )
        }
    }
}