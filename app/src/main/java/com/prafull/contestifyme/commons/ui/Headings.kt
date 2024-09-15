package com.prafull.contestifyme.commons.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 *  @param label: String resource id of the label
 * */
@Composable
fun Headings(
    modifier: Modifier = Modifier,
    @StringRes label: Int,
    upper: Boolean = false
) {
    Text(
        modifier = modifier.padding(vertical = 8.dp),
        text =
        if (upper) {
            stringResource(id = label).uppercase()
        } else {
            stringResource(id = label)
        },
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Start,
    )
}