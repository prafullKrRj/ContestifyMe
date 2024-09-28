package com.prafull.contestifyme.app.friendsFeature.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.commons.ui.ErrorScreen
import com.prafull.contestifyme.app.friendsFeature.domain.FriendData
import com.prafull.contestifyme.app.userscreen.UserProfileScreen
import com.prafull.contestifyme.goBackStack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendScreen(viewModel: FriendScreenViewModel, navController: NavController) {
    val friendState by viewModel.friendState.collectAsState()
    val onBackClick = remember {
        {
            navController.goBackStack()
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = viewModel.friendHandle) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (friendState) {
                is BaseClass.Loading -> {
                    LoadingScreen()
                }

                is BaseClass.Success -> {
                    UserProfileScreen(
                        userData = (friendState as BaseClass.Success<FriendData>).data.toUserData(),
                        modifier = Modifier,
                        navController = navController,
                        true
                    )
                }

                is BaseClass.Error -> {
                    ErrorScreen {
                        viewModel.getFriendDetails()
                    }
                }

            }
        }
    }
}