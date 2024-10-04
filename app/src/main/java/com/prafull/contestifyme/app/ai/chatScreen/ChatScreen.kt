package com.prafull.contestifyme.app.ai.chatScreen


import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText
import com.prafull.contestifyme.R
import com.prafull.contestifyme.app.AiRoutes
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(viewModel: ChatViewModel, navController: NavController) {
    val state by viewModel.uiState.collectAsState()
    val promptValue = rememberSaveable {
        mutableStateOf("")
    }

    val listState = rememberLazyListState()
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    LaunchedEffect(state.messages.size) {
        listState.animateScrollToItem(0)
    }
    val historyState by viewModel.historyState.collectAsState()
    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        ModalDrawerSheet(drawerState = drawerState, Modifier.padding(end = 48.dp)) {
            Column(Modifier.fillMaxWidth()) {
                Row(Modifier.fillMaxWidth()) {
                    Text(
                        text = "Chats",
                        Modifier.padding(16.dp),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                LazyColumn(Modifier.fillMaxSize()) {
                    item {
                        HorizontalDivider()
                    }
                    item {
                        NavigationDrawerItem(
                            label = {
                                Row(
                                    Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = "New Chat")
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_chat_24),
                                        contentDescription = "New Chat"
                                    )
                                }
                            },
                            selected = false,
                            onClick = {
                                viewModel.resetChat()
                                scope.launch { drawerState.close() }
                            })
                    }
                    item {
                        HorizontalDivider()
                    }
                    items(historyState) { chatEntity ->
                        NavigationDrawerItem(
                            label = {
                                Text(text = chatEntity.chatHeading)
                            },
                            selected = chatEntity.chatId == viewModel.chatId,
                            onClick = {
                                viewModel.resetChat(chatEntity.chatId)
                                scope.launch { drawerState.close() }
                            })
                    }
                }
            }
        }
    }) {
        Scaffold(bottomBar = {
            PromptField(promptValue.value, {
                promptValue.value = it
            }) {
                viewModel.sendMessage(promptValue.value)
                promptValue.value = ""
                focusManager.clearFocus()
            }
        }, topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "ContestifyMe Bot") },
                navigationIcon = {
                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(AiRoutes.ApiSettings) }) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        }) { paddingValues ->
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                    start = 8.dp,
                    end = 8.dp
                ),
                modifier = Modifier
                    .fillMaxSize(),
                reverseLayout = true,
            ) {
                item {
                    if (state.messages.isEmpty()) {
                        StartConversationPrompt()
                    }

                }
                items(state.messages.reversed(), key = {
                    it.id
                }) {
                    ChatMessageBubble(it, clipboardManager, context)
                }
            }
        }
    }

}

@Composable
fun StartConversationPrompt() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_chat_24),
                contentDescription = "Start Conversation",
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Start a conversation!",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Type a message to begin chatting with the bot.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun PromptField(value: String, onValueChange: (String) -> Unit, onSent: () -> Unit) {
    OutlinedTextField(value = value, onValueChange = onValueChange,
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp), label = {
            Text(text = "Ask me anything")
        }, trailingIcon = {
            IconButton(onClick = onSent) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = null
                )
            }
        }, colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            focusedLabelColor = MaterialTheme.colorScheme.onSurface,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
            focusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainerLow,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainerLow,
        ), shape = RoundedCornerShape(16.dp), keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Send
        ), keyboardActions = KeyboardActions(onSend = {
            onSent()
        })
    )
}

@Composable
fun ChatMessageBubble(
    message: ChatMessage,
    clipboardManager: ClipboardManager,
    context: Context
) {
    if (message.participant == Participant.USER) {
        if (message.isPending) {
            Row(
                modifier = Modifier
                    .shimmer() // <- Affects all subsequent UI elements
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(text = "✨", Modifier.padding(vertical = 12.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Row(modifier = Modifier) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(24.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.LightGray),
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .weight(.8f)
                                .height(24.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.LightGray),
                        )
                        Spacer(modifier = Modifier.weight(.2f))
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .weight(.65f)
                                .height(24.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.LightGray),
                        )
                        Spacer(modifier = Modifier.weight(.35f))
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .weight(.5f)
                                .height(24.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.LightGray),
                        )
                        Spacer(modifier = Modifier.weight(.5f))
                    }
                }
            }
        }
        UserChatBubble(message)
    } else {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "✨", Modifier.padding(vertical = 12.dp))
            ModelChatBubble(message, clipboardManager, context)
        }
    }
}

@Composable
fun UserChatBubble(message: ChatMessage) {
    Row(Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.weight(.3f))
        Card(
            modifier = Modifier.weight(.7f), colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            val textState = rememberRichTextState()
            textState.setMarkdown(message.text)
            RichText(
                state = textState,
                Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            )
        }
    }
}

@Composable
fun ModelChatBubble(
    message: ChatMessage,
    clipboardManager: ClipboardManager,
    context: Context
) {
    Row(Modifier.fillMaxWidth()) {
        AiMessageBubble(message = message, clipboardManager, context)
    }
}