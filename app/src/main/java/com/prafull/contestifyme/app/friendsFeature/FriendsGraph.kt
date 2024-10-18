package com.prafull.contestifyme.app.friendsFeature

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.prafull.contestifyme.app.App
import com.prafull.contestifyme.app.friendsFeature.ui.comparehandles.CompareScreen
import com.prafull.contestifyme.app.friendsFeature.ui.friendList.FriendListScreen
import com.prafull.contestifyme.app.friendsFeature.ui.friendscreen.FriendScreen
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

fun NavGraphBuilder.friends(navController: NavController) {
    navigation<App.Friends>(startDestination = FriendsRoutes.FriendsList) {
        composable<FriendsRoutes.FriendsList> {
            FriendListScreen(viewModel = getViewModel(), navController)
        }
        composable<FriendsRoutes.FriendScreen> {
            val handle = it.toRoute<FriendsRoutes.FriendScreen>()
            FriendScreen(viewModel = getViewModel { parametersOf(handle.handle) }, navController)
        }
        composable<FriendsRoutes.CompareScreen> {
            CompareScreen(getViewModel(), navController)
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

    @Serializable
    data object CompareScreen : FriendsRoutes
}