package com.prafull.contestifyme.app.friendsFeature.ui.friendscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.prafull.contestifyme.app.friendsFeature.ui.friendList.LoadingScreen
import com.prafull.contestifyme.app.userscreen.UserScreen
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
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = viewModel.friendHandle) }, navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }, modifier = Modifier.fillMaxWidth()
        )
    }) { paddingValues ->

        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val response = friendState) {
                is BaseClass.Loading -> {
                    LoadingScreen()
                }

                is BaseClass.Success -> {
                    UserScreen(
                        userData = response.data,
                        navController = navController
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