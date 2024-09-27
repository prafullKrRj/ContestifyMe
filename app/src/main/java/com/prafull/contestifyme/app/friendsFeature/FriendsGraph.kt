package com.prafull.contestifyme.app.friendsFeature

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.prafull.contestifyme.app.App
import com.prafull.contestifyme.app.friendsFeature.ui.FriendListScreen
import com.prafull.contestifyme.app.friendsFeature.ui.FriendScreen
import com.prafull.contestifyme.app.friendsFeature.ui.FriendsViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

fun NavGraphBuilder.friends(navController: NavController, friendsViewModel: FriendsViewModel) {
    navigation<App.Friends>(startDestination = FriendsRoutes.FriendsList) {
        composable<FriendsRoutes.FriendsList> {
            FriendListScreen(viewModel = friendsViewModel, navController)
        }
        composable<FriendsRoutes.FriendScreen> {
            val handle = it.toRoute<FriendsRoutes.FriendScreen>()
            FriendScreen(viewModel = getViewModel { parametersOf(handle.handle) }, navController)
        }
    }
}

sealed interface FriendsRoutes {

    @Serializable
    data class FriendScreen(
        val handle: String
    ) : FriendsRoutes

    @Serializable
    data object FriendsList : FriendsRoutes
}