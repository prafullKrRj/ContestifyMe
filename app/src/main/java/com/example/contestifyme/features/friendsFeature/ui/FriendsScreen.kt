package com.example.contestifyme.features.friendsFeature.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun FriendsScreen(
    viewModel: FriendsViewModel
) {
    val state by viewModel.friendsUiState.collectAsState()
    if (state.friends.isEmpty()) {
        NoFriendsScreen {
            viewModel.getFriends(listOf(it))
        }
    } else {
        FriendsListScreen(friends =  state.friends, onFriendClicked = {
            viewModel.getFriends(listOf(it))
        }, addFriend = {
            viewModel.getFriends(listOf(it))
        })
    }
}
