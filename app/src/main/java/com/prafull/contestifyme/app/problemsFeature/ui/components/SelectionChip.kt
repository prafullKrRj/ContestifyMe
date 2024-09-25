package com.prafull.contestifyme.app.problemsFeature.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

@ExperimentalMaterial3Api
@Composable
fun SelectionChip(selected: Boolean, title: String, icon: Int, onClick: () -> Unit) {
    FilterChip(
        selected = selected,
        onClick = {
            onClick()
        },
        label = {
            Text(title)
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "Done",
                modifier = Modifier.size(FilterChipDefaults.IconSize)
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = Color.Transparent,
            selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    )
}