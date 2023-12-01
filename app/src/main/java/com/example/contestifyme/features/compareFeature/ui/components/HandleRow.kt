package com.example.contestifyme.features.compareFeature.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableInferredTarget
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HandleSelection(
    modifier: Modifier = Modifier,
    handle: (String) -> Unit,
) {
    var enabled by rememberSaveable { mutableStateOf(true) }
    var text by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        enabled = enabled,
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        value = text,
        onValueChange = {
            text = it
            handle(it)
        },
        shape = RoundedCornerShape(35),
        label = { Text("handle") },
        leadingIcon = {

                      },
    )
}

@Composable
private fun GoIconButton() {

}