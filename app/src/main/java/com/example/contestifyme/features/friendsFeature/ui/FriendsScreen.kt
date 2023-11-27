package com.example.contestifyme.features.friendsFeature.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.contestifyme.features.friendsFeature.data.local.FriendsDataEntity
import com.example.contestifyme.features.profileFeature.ui.SubmissionAnswer

@Composable
fun FriendsScreen(
    viewModel: FriendsViewModel
) {
    val state by viewModel.friendsUiState.collectAsState()
    MainFriendsUI(state = state, viewModel = viewModel, friends = state.friends)
}

@Composable
fun MainFriendsUI(state: FriendsUiState, viewModel: FriendsViewModel, friends: List<FriendsDataEntity>) {
    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            FriendsListScreen(friends =  friends, onFriendClicked = {
                navController.navigate("detail/$it")
            }, addFriend = {
                viewModel.addFriends(listOf(it))
            }, state)
        }
        composable("detail/{handle}") {navBackStackEntry ->
            navBackStackEntry.arguments?.getString("handle")?.let {
                FriendsDetailScreen(
                    navHostController = navController,
                    viewModel.getFriend(it)
                )
            }
        }
        composable("answer") {
            SubmissionAnswer(
                url = "https://www.codeforces.com/"
            )
        }
    }
}

