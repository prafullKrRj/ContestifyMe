package com.example.contestifyme.commons.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SubmissionsGraph() {

    val state = rememberLazyListState()
    val scope = rememberCoroutineScope()
    scope.launch {
        state.animateScrollToItem(6*30)
    }
    LazyRow(state = state) {
        repeat(6) {
            item {
                Column {
                    repeat(7) {
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .size(14.dp)
                                .clip(shape = RoundedCornerShape(4.dp))
                                .background(Color.Green)
                        )
                    }
                }
            }
        }
    }
}