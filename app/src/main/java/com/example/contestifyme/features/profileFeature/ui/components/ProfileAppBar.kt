package com.example.contestifyme.features.profileFeature.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.contestifyme.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileAppBar(page: Int, iconClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Row {
                Image(painter = painterResource(id = R.drawable.logo2), contentDescription = null, Modifier.size(32.dp))
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text =
                    if (page == 0) stringResource(id = R.string.app_name) else stringResource(id = R.string.submissions),
                    maxLines = 1, overflow = TextOverflow.Ellipsis, fontFamily = FontFamily.SansSerif,
                )
            }
        },
        actions = {
            IconButton(onClick = {
                iconClick()
            }) {
                Icon(
                    imageVector = if (page == 0) Icons.Default.Settings else Icons.Default.Info,
                    contentDescription = "App Info or Settings"
                )
            }
        }
    )
}