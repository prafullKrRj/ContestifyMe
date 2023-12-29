package com.example.contestifyme.features.codeAssistantFeature.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ComparatorItem(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        item {
            HandleSelection(handle = {

            })
        }
    }
}