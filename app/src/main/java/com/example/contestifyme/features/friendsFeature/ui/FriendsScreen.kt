package com.example.contestifyme.features.friendsFeature.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.contestifyme.features.profileFeature.ui.SubmissionAnswer

@Composable
fun FriendsScreen(
    viewModel: FriendsViewModel
) {
    val state by viewModel.friendsUiState.collectAsState()
    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            FriendsListScreen(friends =  state.friends, onFriendClicked = {
                viewModel.loading = true
                navController.navigate("detail/$it")
            }) {
                viewModel.addFriends(listOf(it))
            }
        }
        composable("detail/{handle}") {navBackStackEntry ->
            navBackStackEntry.arguments?.getString("handle")?.let {
                if (viewModel.loading) {
                    LoadingScreen()
                    viewModel.updateDetails(it)
                } else {
                    FriendsDetailScreen(
                        navHostController = navController,
                        it,
                        viewModel
                    )
                }
            }
        }
        composable("answer") {
            SubmissionAnswer(
                url = "https://www.codeforces.com/"
            )
        }
    }
}
@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}