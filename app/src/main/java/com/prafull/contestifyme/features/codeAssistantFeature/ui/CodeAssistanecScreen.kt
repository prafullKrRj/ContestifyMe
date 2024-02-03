package com.prafull.contestifyme.features.codeAssistantFeature.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.example.contestifyme.R
import com.prafull.contestifyme.commons.ui.SimpleTopAppBar
import com.prafull.contestifyme.ui.AppViewModelProvider
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun CodeAssistanceScreen(
    viewModel: CodeAssistanceViewModel
) {
    Scaffold(
        topBar = {
            SimpleTopAppBar(
                label = R.string.coder,
            )
        },
        bottomBar = {

        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            LazyColumn {
                viewModel.chatMessages.forEach {
                    item {
                        if (it.role == ChatRole.User) {
                            Row(
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                ChatBubble(modifier = Modifier.padding(start = 16.dp, end = 4.dp), message = it, shape = RoundedCornerShape(
                                    topEnd = 0.dp,
                                    topStart = 25.dp,
                                    bottomEnd = 25.dp,
                                    bottomStart = 25.dp
                                ))
                            }
                        } else {
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                ChatBubble(modifier = Modifier.padding(end = 12.dp, start = 4.dp), message = it, shape = RoundedCornerShape(
                                    topEnd = 25.dp,
                                    topStart = 0.dp,
                                    bottomEnd = 25.dp,
                                    bottomStart = 25.dp
                                ))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BannerAd() {
    AndroidView(
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                this.adUnitId = "ca-app-pub-3940256099942544/6300978111"
                loadAd(AdRequest.Builder().build())
                Log.d("add view", "working")
            }
        }
    )
}
@Composable
fun ChatBubble(modifier: Modifier, message: ChatMessage, shape: RoundedCornerShape) {
    Card(modifier = modifier
        .padding(vertical = 6.dp),
        shape = shape
    ) {
        Text(text = message.content?: "Error", Modifier.padding(6.dp))
    }
}
@Composable
fun DataInputField(modifier: Modifier, updateChat: (String) -> Unit) {

    var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("", TextRange(0, 7)))
    }
    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Prompt") },
        trailingIcon = {
            if (text.text.isNotEmpty()) {
                IconButton(onClick = {
                    updateChat(text.text)
                    text = TextFieldValue("", TextRange(0, 7))
                }) {
                    Icon(
                        Icons.Filled.Send,
                        contentDescription = "Send message"
                    )
                }
            }
        },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(50),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    )
}
@Preview(showSystemUi = true)
@Composable
fun CompareScreenPreview() {
    CodeAssistanceScreen(viewModel = viewModel(factory = AppViewModelProvider.compareVm))
}