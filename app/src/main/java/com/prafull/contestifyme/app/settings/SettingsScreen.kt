package com.prafull.contestifyme.app.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafull.contestifyme.R
import com.prafull.contestifyme.goBackStack


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: SettingViewModel, navController: NavController) {

    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var logout by remember {
        mutableStateOf(false)
    }
    var changeHandle by remember {
        mutableStateOf(false)
    }
    var deleteAllFriends by remember {
        mutableStateOf(false)
    }
    var deleteApiKey by remember {
        mutableStateOf(false)
    }
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Settings") }, navigationIcon = {
            IconButton(onClick = navController::goBackStack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "BackStack"
                )
            }
        })
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ColumnItem(
                icon = ImageVector.vectorResource(id = R.drawable.baseline_logout_24),
                headline = "Logout"
            ) {
                logout = true
            }
            ColumnItem(
                icon = Icons.Default.Create,
                headline = "Change Handle"
            ) {
                changeHandle = true
            }
            ColumnItem(
                icon = Icons.Default.Delete.apply { this.tintColor.red },
                headline = "Delete All friends"
            ) {
                deleteAllFriends = true
            }
            ColumnItem(
                icon = Icons.Default.Delete.apply { this.tintColor.red },
                headline = "Delete Api Key"
            ) {
                deleteApiKey = true
            }
            HorizontalDivider(Modifier.padding(vertical = 8.dp))
            ColumnItem(
                icon = ImageVector.vectorResource(id = R.drawable.baseline_web_24),
                headline = "Privacy Policy"
            ) {
            }
            ColumnItem(
                icon = ImageVector.vectorResource(id = R.drawable.baseline_library_books_24),
                headline = "Libraries Used"
            ) {

            }
        }
    }
    if (logout) {
        AlertBox(
            title = "Logout",
            text = "Are you sure you want to logout?",
            onConfirm = {
                logout = false
            },
            onDismiss = {
                logout = false
            }
        )
    }
    if (deleteAllFriends) {
        AlertBox(
            title = "Delete All Friends",
            text = "Are you sure you want to delete all friends?",
            onConfirm = {
                viewModel.deleteAllFriends()
                deleteAllFriends = false
            },
            onDismiss = {
                deleteAllFriends = false
            }
        )
    }
    if (deleteApiKey) {
        AlertBox(
            title = "Delete Api Key",
            text = "Are you sure you want to delete api key?",
            onConfirm = {
                deleteApiKey = false
                viewModel.deleteApiKey(context)
            },
            onDismiss = {
                deleteApiKey = false
            }
        )
    }
}

@Composable
fun AlertBox(title: String, text: String, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(onDismissRequest = onDismiss, confirmButton = {
        TextButton(onClick = onConfirm) {
            Text(text = "Confirm")
        }
    }, dismissButton = {
        TextButton(onClick = onDismiss) {
            Text(text = "Cancel")
        }
    }, title = {
        Text(text = title)
    }, text = {
        Text(text = text)
    })
}

@Composable
fun ColumnItem(icon: ImageVector, headline: String, onClick: () -> Unit) {
    ListItem(headlineContent = { Text(text = headline) }, leadingContent = {
        Icon(
            imageVector = icon, contentDescription = headline
        )
    }, modifier = Modifier
        .fillMaxWidth()
        .clickable {
            onClick()
        }
        .padding(4.dp))
}