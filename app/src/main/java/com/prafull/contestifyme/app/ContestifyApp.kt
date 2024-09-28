package com.prafull.contestifyme.app

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.prafull.contestifyme.R
import com.prafull.contestifyme.app.contestsFeature.contestListScreen.ContestsScreen
import com.prafull.contestifyme.app.contestsFeature.contestListScreen.ContestsViewModel
import com.prafull.contestifyme.app.contestsFeature.contestScreen.ContestScreen
import com.prafull.contestifyme.app.friendsFeature.friends
import com.prafull.contestifyme.app.friendsFeature.ui.FriendsViewModel
import com.prafull.contestifyme.app.problemsFeature.ui.ProblemsMain
import com.prafull.contestifyme.app.problemsFeature.ui.ProblemsViewModel
import com.prafull.contestifyme.app.profileFeature.ui.ProfileScreen
import com.prafull.contestifyme.app.profileFeature.ui.ProfileViewModel
import com.prafull.contestifyme.app.userscreen.submissions.Submissions
import com.prafull.contestifyme.app.webview.WebViewComposable
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ContestifyAPP() {
    val navController = rememberNavController()
    ContestifyMainApp(navController = navController)
}

@SuppressLint("RestrictedApi")
@Composable
fun ContestifyMainApp(navController: NavHostController) {
    val viewModelStoreOwner = LocalViewModelStoreOwner.current!!
    val currDestination = navController.currentBackStackEntryAsState().value?.destination?.route
    LaunchedEffect(key1 = currDestination) {
        Log.d("ContestifyMainApp", "Current Destination: $currDestination")
    }
    val profileViewModel: ProfileViewModel = getViewModel(viewModelStoreOwner = viewModelStoreOwner)
    val contestsViewModel: ContestsViewModel =
        getViewModel(viewModelStoreOwner = viewModelStoreOwner)
    val friendsViewModel: FriendsViewModel = getViewModel(viewModelStoreOwner = viewModelStoreOwner)
    val problemsViewModel: ProblemsViewModel =
        getViewModel(viewModelStoreOwner = viewModelStoreOwner)
    val selected = rememberSaveable { mutableIntStateOf(0) }
    Scaffold(
        bottomBar = {
            if (canShowBottomBar(
                    currDestination ?: ""
                )
            ) AppBottomAppBar(navController = navController, selected = selected.intValue) {
                selected.intValue = it
            }
        },
    ) { paddingValues ->
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            navController = navController,
            startDestination = App.Profile
        ) {
            composable<App.Profile> {
                ProfileScreen(viewModel = profileViewModel, navController)
            }
            composable<App.SubmissionScreen> {
                val submissions: String = it.toRoute<App.SubmissionScreen>().submissions
                Submissions(getViewModel { parametersOf(submissions) }, navController)
            }
            friends(navController, friendsViewModel)
            composable<App.WebViewScreen> {
                val item = it.toRoute<App.WebViewScreen>()
                WebViewComposable(item.url, item.heading, navController)
            }
            composable<App.Problems> {
                ProblemsMain(viewModel = problemsViewModel, navController = navController)
            }
            navigation<App.Contests>(ContestRoutes.ContestList) {
                composable<ContestRoutes.ContestList> {
                    ContestsScreen(viewModel = contestsViewModel, navController)
                }
                composable<ContestRoutes.ContestScreen> {
                    val item = it.toRoute<ContestRoutes.ContestScreen>()
                    ContestScreen(
                        viewModel = koinViewModel { parametersOf(item) },
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun AppBottomAppBar(navController: NavController, selected: Int, onItemSelected: (Int) -> Unit) {
    NavigationBar(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
    ) {
        ContestifyScreens.entries.forEachIndexed { index, contestifyScreens ->
            NavigationBarItem(selected = selected == index, onClick = {
                navController.navigate(contestifyScreens.route)
                onItemSelected(index)
            }, label = {
                Text(text = contestifyScreens.itemName)
            }, icon = {
                if (selected == index) {
                    Icon(
                        painter = painterResource(id = contestifyScreens.selectedIcon),
                        contentDescription = "Selected Icon"
                    )
                } else {
                    Icon(
                        painter = painterResource(id = contestifyScreens.unSelectedIcon),
                        contentDescription = "Unselected Icon"
                    )
                }
            })
        }
    }
}

enum class ContestifyScreens(
    val itemName: String,
    val route: Any,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unSelectedIcon: Int
) {
    PROFILE(
        "Profile", App.Profile, R.drawable.profile_filled, R.drawable.profile
    ),
    CONTESTS("Contests", App.Contests, R.drawable.contest, R.drawable.contest), FRIENDS(
        "Friends", App.Friends, R.drawable.friends_filled, R.drawable.friend
    ),
    PROBLEMS("Problems", App.Problems, R.drawable.problems, R.drawable.problems)
}

fun canShowBottomBar(currRoute: String): Boolean {
    return currRoute != "com.prafull.contestifyme.app.friendsFeature.FriendsRoutes.FriendScreen/{handle}" && currRoute != "com.prafull.contestifyme.app.App.WebViewScreen/{url}/{heading}"
            && currRoute != "com.prafull.contestifyme.app.ContestRoutes.ContestScreen/{contestId}/{contestName}"
            && currRoute != "com.prafull.contestifyme.app.App.SubmissionScreen/{submissions}"
}