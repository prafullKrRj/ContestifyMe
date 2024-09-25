package com.prafull.contestifyme.app.commons.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ErrorScreen(error: String = "Error in Loading", onRetry: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            modifier = Modifier,
            text = error,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Start,
        )
        FilledTonalButton(
            onClick = {
                onRetry()
            }
        ) {
            Text("Retry")
        }
    }
}