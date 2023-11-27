package com.example.contestifyme.features.profileFeature.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun DetailsDialog(content: @Composable () -> Unit, dismiss: () -> Unit = {}) {
    val scrollState = rememberScrollState()
    Dialog(
        onDismissRequest = {
            dismiss()
        },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
    ) {
        ElevatedCard(modifier = Modifier.padding(vertical = 8.dp)) {
            Column(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 12.dp).verticalScroll(scrollState)
            ) {
                content()
                TextButton(onClick = { dismiss() }, modifier = Modifier.align(Alignment.End)) {
                    Text(text = "Ok", textAlign = TextAlign.End)
                }
            }
        }
    }
}