package com.prafull.contestifyme.app.ai.chatScreen


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.asTextOrNull
import com.prafull.contestifyme.app.ai.di.ApiKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import java.util.UUID

class ChatViewModel(
    private val apiKey: ApiKey
) : ViewModel(), KoinComponent {


    private var generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = apiKey.apiKey,
    )

    private val chat = generativeModel.startChat(
        history = listOf()
    )

    private val _uiState: MutableStateFlow<ChatUiState> =
        MutableStateFlow(ChatUiState(chat.history.map { content ->
            // Map the initial messages
            ChatMessage(
                text = content.parts.first().asTextOrNull() ?: "",
                participant = if (content.role == "user") Participant.USER else Participant.MODEL,
                isPending = false
            )
        }))


    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()
    val isLoading by mutableStateOf(false)

    fun sendMessage(userMessage: String) {
        // Add a pending message
        _uiState.value.addMessage(
            ChatMessage(
                text = userMessage, participant = Participant.USER, isPending = true
            )
        )

        viewModelScope.launch {
            try {
                val response = chat.sendMessage(userMessage)

                response.text?.let { modelResponse ->
                    val last = _uiState.value.messages.last()
                    _uiState.update {
                        ChatUiState(
                            messages = _uiState.value.messages.dropLast(1) + last.copy(isPending = false)
                        )
                    }
                    _uiState.value.addMessage(
                        ChatMessage(
                            text = modelResponse, participant = Participant.MODEL, isPending = false
                        )
                    )
                }
            } catch (e: Exception) {
                _uiState.value.replaceLastPendingMessage()
                _uiState.value.addMessage(
                    ChatMessage(
                        text = e.localizedMessage?.toString() ?: "Error",
                        participant = Participant.ERROR
                    )
                )
            }
        }
    }
}

enum class Participant {
    USER, MODEL, ERROR
}

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    var text: String = "",
    val participant: Participant = Participant.USER,
    var isPending: Boolean = true
)

class ChatUiState(
    messages: List<ChatMessage> = emptyList()
) {
    private val _messages: MutableList<ChatMessage> = messages.toMutableStateList()
    val messages: List<ChatMessage> = _messages

    fun addMessage(msg: ChatMessage) {
        _messages.add(msg)
    }

    fun replaceLastPendingMessage() {
        val lastMessage = _messages.lastOrNull()
        lastMessage?.let {
            val newMessage = lastMessage.apply { isPending = false }
            _messages.removeLast()
            _messages.add(newMessage)
        }
    }
}