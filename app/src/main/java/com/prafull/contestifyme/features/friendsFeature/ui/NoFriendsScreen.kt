package com.prafull.contestifyme.features.friendsFeature.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
@Composable
fun NoFriendsScreen(handle: (String) -> Unit) {
    var inputHandle by rememberSaveable { mutableStateOf("") }
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "No friends yet!")
        OutlinedTextField(
            value = inputHandle,
            onValueChange = { inputHandle = it },
            label = { Text("Label") },
        )
        ElevatedButton(
            onClick = {
                handle(inputHandle)
            }
        ) {
            Text(text = "Go")
        }
    }
}