package com.prafull.contestifyme.app.profileFeature.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafull.contestifyme.R
import com.prafull.contestifyme.app.App
import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.commons.ui.ErrorScreen
import com.prafull.contestifyme.app.friendsFeature.ui.friendList.LoadingScreen
import com.prafull.contestifyme.app.userscreen.UserProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel, navController: NavController) {
    val state by viewModel.profileUiState.collectAsState()
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.logo2),
                    contentDescription = null,
                    Modifier.size(32.dp)
                )
                Text(
                    text = stringResource(id = R.string.app_name),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = FontFamily.SansSerif,
                )
            }
        }, actions = {
            IconButton(onClick = {
                navController.navigate(App.Settings)
            }) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "To Settings")
            }
        })
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val response = state) {
                is BaseClass.Error -> {
                    Text(
                        text = response.exception.toString(),
                        color = androidx.compose.ui.graphics.Color.Red
                    )
                    ErrorScreen {
                        viewModel.updateUserInfo()
                    }
                }

                BaseClass.Loading -> {
                    LoadingScreen()
                }

                is BaseClass.Success -> {
                    UserProfileScreen(
                        userData = response.data,
                        Modifier
                    ) {
                        navController.navigate(App.SubmissionScreen)
                    }
                }
            }
        }
    }
}