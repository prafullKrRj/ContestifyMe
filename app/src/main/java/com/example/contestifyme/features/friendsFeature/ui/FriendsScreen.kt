package com.example.contestifyme.features.friendsFeature.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun FriendsScreen(
    viewModel: FriendsViewModel
) {
    val state by viewModel.friendsUiState.collectAsState()
    if (state.friends.isEmpty()) {
        UpdateUserHandle {
            viewModel.getFriends(listOf(it))
        }
    } else {
        Column {
            UpdateUserHandle { it ->
                viewModel.getFriends(listOf(it))
            }
            state.friends.forEach {
                Text(text = it.handle+" "+it.name+" "+it.country+" "+ it.rank+" "+it.rating+" "+it.registrationTimeSeconds)
            }
        }
    }
}

@Composable
fun UpdateUserHandle(handle: (String) -> Unit) {
    var text by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Label") },
        trailingIcon = {
            IconButton(
                onClick = {
                    handle(text)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Send,
                    contentDescription = "Go"
                )
            }
        }
    )
}