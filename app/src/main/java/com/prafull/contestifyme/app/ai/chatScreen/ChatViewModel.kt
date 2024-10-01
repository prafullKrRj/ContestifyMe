package com.prafull.contestifyme.app.ai.chatScreen


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.TextPart
import com.google.ai.client.generativeai.type.asTextOrNull
import com.prafull.contestifyme.app.ai.data.ChatDao
import com.prafull.contestifyme.app.ai.data.ChatEntity
import com.prafull.contestifyme.app.ai.di.ApiKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.UUID

class ChatViewModel(
    private val apiKey: ApiKey, private val passedChatId: String = UUID.randomUUID().toString()
) : ViewModel(), KoinComponent {


    var chatId by mutableStateOf(passedChatId)

    private var generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = apiKey.apiKey,
    )
    private val chatDao by inject<ChatDao>()

    val historyState = chatDao.getAllChats()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    private var _chat = MutableStateFlow<Chat>(generativeModel.startChat())
    val chat = _chat.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val chatEntity = chatDao.getChatById(passedChatId)
            if (chatEntity != null) {
                _chat.update {
                    generativeModel.startChat(
                        history = chatEntity.messages.map { it.toContent() }
                    )
                }
            }
        }
    }

    private val _uiState: MutableStateFlow<ChatUiState> =
        MutableStateFlow(ChatUiState(chat.value.history.map { content ->
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
                val response = _chat.value.sendMessage(
                    ChatMessage(
                        text = userMessage, participant = Participant.USER
                    ).toContent()
                )

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
            chatDao.insertChat(
                ChatEntity(
                    chatId = chatId,
                    chatHeading = uiState.value.messages.firstOrNull()?.text ?: "",
                    lastModified = System.currentTimeMillis(),
                    messages = _uiState.value.messages
                )
            )
        }
    }

    fun resetChat(id: String = UUID.randomUUID().toString()) {
        chatId = id
        viewModelScope.launch {
            val entity = chatDao.getChatById(id)
            if (entity != null) {
                _chat.update {
                    generativeModel.startChat(
                        history = entity.messages.map { it.toContent() }
                    )
                }
                _uiState.update {
                    ChatUiState(
                        messages = entity.messages.map { chatMessage ->
                            chatMessage.copy(isPending = false)
                        }
                    )
                }
            } else {
                _chat.update {
                    generativeModel.startChat()
                }
                _uiState.update {
                    ChatUiState()
                }
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
) {
    fun toContent() = Content(
        parts = listOf(TextPart(text)), role = when (participant) {
            Participant.USER -> "user"
            Participant.MODEL -> "model"
            Participant.ERROR -> "error"
        }
    )
}

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