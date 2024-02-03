package com.prafull.contestifyme.commons.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RankCard(
    modifier: Modifier,
    rank: String,
    handle: String,
    country: String
) {
    ElevatedCard {
        Column(
            modifier = modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(text = rank)
            Text(text = handle)
            Text(text = country)
        }
    }
}