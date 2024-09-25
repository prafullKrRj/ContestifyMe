package com.prafull.contestifyme.app

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
import androidx.navigation.compose.rememberNavController
import com.example.contestifyme.R
import com.prafull.contestifyme.App
import com.prafull.contestifyme.app.contestsFeature.ui.ContestsScreen
import com.prafull.contestifyme.app.contestsFeature.ui.ContestsViewModel
import com.prafull.contestifyme.app.friendsFeature.ui.FriendsScreen
import com.prafull.contestifyme.app.friendsFeature.ui.FriendsViewModel
import com.prafull.contestifyme.app.problemsFeature.ui.ProblemsScreen
import com.prafull.contestifyme.app.problemsFeature.ui.ProblemsViewModel
import com.prafull.contestifyme.app.profileFeature.ui.ProfileScreen
import com.prafull.contestifyme.app.profileFeature.ui.ProfileViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun ContestifyAPP() {
    val navController = rememberNavController()
    ContestifyMainApp(navController = navController)
}

@Composable
fun ContestifyMainApp(navController: NavHostController) {
    val viewModelStoreOwner = LocalViewModelStoreOwner.current!!


    val profileViewModel: ProfileViewModel = getViewModel(viewModelStoreOwner = viewModelStoreOwner)
    val contestsViewModel: ContestsViewModel =
        getViewModel(viewModelStoreOwner = viewModelStoreOwner)
    val friendsViewModel: FriendsViewModel = getViewModel(viewModelStoreOwner = viewModelStoreOwner)
    val problemsViewModel: ProblemsViewModel =
        getViewModel(viewModelStoreOwner = viewModelStoreOwner)

    Scaffold(
        bottomBar = {
            val selected = rememberSaveable { mutableIntStateOf(0) }
            AppBottomAppBar(navController = navController, selected = selected.intValue) {
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
            composable<App.Contests> {
                ContestsScreen(viewModel = contestsViewModel)
            }
            composable<App.Friends> {
                FriendsScreen(viewModel = friendsViewModel)
            }
            composable<App.Problems> {
                ProblemsScreen(viewModel = problemsViewModel)
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
        "Profile",
        App.Profile,
        R.drawable.profile_filled,
        R.drawable.profile
    ),
    CONTESTS("Contests", App.Contests, R.drawable.contest, R.drawable.contest), FRIENDS(
        "Friends",
        App.Friends,
        R.drawable.friends_filled,
        R.drawable.friend
    ),
    PROBLEMS("Problems", App.Problems, R.drawable.problems, R.drawable.problems)
}