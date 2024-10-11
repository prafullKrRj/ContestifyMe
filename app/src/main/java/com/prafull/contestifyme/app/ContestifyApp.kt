package com.prafull.contestifyme.app

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
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
import com.prafull.contestifyme.app.ai.aiSettings.AiSettingsScreen
import com.prafull.contestifyme.app.ai.chatScreen.AiConst
import com.prafull.contestifyme.app.ai.chatScreen.ChatScreen
import com.prafull.contestifyme.app.ai.enrollToAI.EnrollingScreen
import com.prafull.contestifyme.app.contestsFeature.contestListScreen.ContestsScreen
import com.prafull.contestifyme.app.contestsFeature.contestListScreen.ContestsViewModel
import com.prafull.contestifyme.app.contestsFeature.contestScreen.ContestScreen
import com.prafull.contestifyme.app.friendsFeature.friends
import com.prafull.contestifyme.app.friendsFeature.ui.friendList.FriendsViewModel
import com.prafull.contestifyme.app.problemsFeature.ProblemRoutes
import com.prafull.contestifyme.app.problemsFeature.ui.ProblemsMain
import com.prafull.contestifyme.app.problemsFeature.ui.ProblemsViewModel
import com.prafull.contestifyme.app.problemsFeature.ui.acsmsguru.AcmsScreen
import com.prafull.contestifyme.app.profileFeature.ui.ProfileScreen
import com.prafull.contestifyme.app.profileFeature.ui.ProfileViewModel
import com.prafull.contestifyme.app.settings.SettingsScreen
import com.prafull.contestifyme.app.webview.WebViewComposable
import com.prafull.contestifyme.goBackStack
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
fun ContestifyMainApp(navController: NavHostController, logout: () -> Unit = {}) {
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
    val context = LocalContext.current
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
            composable<App.Settings> {
                SettingsScreen(
                    viewModel = getViewModel(),
                    navController = navController
                )
            }
            composable<App.LibrariesList> {
                Text(text = "Libraries")
            }
            friends(navController, friendsViewModel)
            composable<App.WebViewScreen> {
                val item = it.toRoute<App.WebViewScreen>()
                WebViewComposable(item.url, item.heading, navController)
            }
            navigation<App.AI>(
                startDestination = if (context.getSharedPreferences(
                        AiConst.API_PREF_KEY, Context.MODE_PRIVATE
                    ).getBoolean(
                        AiConst.IS_KEY_SAVED, false
                    )
                ) AiRoutes.ChatScreen else AiRoutes.EnrollAi
            ) {
                composable<AiRoutes.ChatScreen> {
                    ChatScreen(viewModel = getViewModel(), navController)
                }
                composable<AiRoutes.EnrollAi> {
                    EnrollingScreen(
                        viewModel = getViewModel()
                    ) {
                        navController.goBackStack()
                        navController.navigate(AiRoutes.ChatScreen)
                    }
                }
                composable<AiRoutes.ApiSettings> {
                    AiSettingsScreen(getViewModel(), navController)
                }
            }
            navigation<App.Problems>(ProblemRoutes.ProblemsMain) {
                composable<ProblemRoutes.ProblemsMain> {
                    ProblemsMain(viewModel = problemsViewModel, navController = navController)
                }
                composable<ProblemRoutes.AcmsGuru> {
                    AcmsScreen(viewModel = getViewModel(), navController)
                }
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
    CONTESTS("Contests", App.Contests, R.drawable.contest, R.drawable.contest), AI(
        "AI",
        App.AI,
        R.drawable.ai_icon,
        R.drawable.ai_icon
    ),
    FRIENDS(
        "Friends", App.Friends, R.drawable.friends_filled, R.drawable.friend
    ),
    PROBLEMS("Problems", App.Problems, R.drawable.problems, R.drawable.problems)
}

fun canShowBottomBar(currRoute: String): Boolean {
    return currRoute != "com.prafull.contestifyme.app.friendsFeature.FriendsRoutes.FriendScreen/{handle}"
            && currRoute != "com.prafull.contestifyme.app.App.WebViewScreen/{url}/{heading}" &&
            currRoute != "com.prafull.contestifyme.app.ContestRoutes.ContestScreen/{contestId}/{contestName}"
            && currRoute != "com.prafull.contestifyme.app.App.SubmissionScreen"
            && currRoute != "com.prafull.contestifyme.app.problemsFeature.ProblemRoutes.AcmsGuru"
            && currRoute != "com.prafull.contestifyme.app.friendsFeature.FriendsRoutes.CompareScreen"
            && currRoute != "com.prafull.contestifyme.app.AiRoutes.ApiSettings"
            && currRoute != "com.prafull.contestifyme.app.App.Settings"
            && currRoute != "com.prafull.contestifyme.app.App.LibrariesList"
}