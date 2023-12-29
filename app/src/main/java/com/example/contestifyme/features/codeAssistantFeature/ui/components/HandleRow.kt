package com.example.contestifyme.features.codeAssistantFeature.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun HandleSelection(
    modifier: Modifier = Modifier,
    handle: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    Column{
        var handleFromDropDown by rememberSaveable { mutableStateOf("") }
        SelectFromKnowns(handles = listOf("a", "b", "c"), selectedHandle = {
            handleFromDropDown = it
        })
        if (handleFromDropDown.isEmpty()) {
            HandleTextField(value = handleFromDropDown, fh = false) {
                handle(it)
                focusManager.clearFocus()
            }
        } else {
            HandleTextField(value = handleFromDropDown, fh = true) {
                handle(it)
                focusManager.clearFocus()
            }
        }
    }
}

@Composable
fun HandleTextField(modifier: Modifier = Modifier, value: String = "", fh: Boolean, handle: (String) -> Unit) {
    var fromHandle by rememberSaveable { mutableStateOf(fh) }
    var text by rememberSaveable { mutableStateOf("") }
    var enabled by rememberSaveable { mutableStateOf(!fh && text.isEmpty()) }
    OutlinedTextField(
        enabled = enabled,
        modifier = modifier
            .padding(horizontal = 12.dp)
            .fillMaxSize()
            .clickable {
                enabled = true
            },
        value = if (fromHandle) value else text,
        onValueChange = {
            fromHandle = false
            text = it
        },
        shape = RoundedCornerShape(50),
        label = {
                if (enabled) {
                    Text(text = "handle")
                }
        },
        trailingIcon = {
            if (text.isNotEmpty()) {
                GoIconButton {
                    handle(text)
                    enabled = false
                }
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search, autoCorrect = true),
        keyboardActions = KeyboardActions(
            onSearch = {
                handle(text)
                enabled = false
            }
        )
    )
}

@Composable
fun SelectFromKnowns(modifier: Modifier = Modifier, handles: List<String>, selectedHandle: (String) -> Unit) {
    var showMenu by rememberSaveable { mutableStateOf(false) }
    Box(modifier = Modifier.padding(horizontal = 8.dp)) {
        TextButton(
            onClick = {
                showMenu = true
            },
        ) {
            Text("friends", textAlign = TextAlign.Start)
        }
        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false }
        ) {
            handles.forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = {
                        selectedHandle(it)
                        showMenu = false
                    }
                )
            }
        }

    }
}
@Composable
private fun GoIconButton(
    onClick: () -> Unit,
) {
    IconButton(
        onClick = {
            onClick()
        }
    ) {
        Icon(
            imageVector = Icons.Filled.Send,
            contentDescription = "Search for handle"
        )
    }
}