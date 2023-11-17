package com.example.contestifyme

import androidx.compose.foundation.layout.Arrangement.SpaceEvenly
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.ui.Modifier
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.contestifyme.Screens.PROFILE
import com.example.contestifyme.Screens.COMPARE
import com.example.contestifyme.Screens.CONTESTS
import com.example.contestifyme.Screens.FRIENDS
import com.example.contestifyme.Screens.PROBLEMS
import com.example.contestifyme.compareFeature.ui.CompareScreen
import com.example.contestifyme.compareFeature.ui.CompareViewModel
import com.example.contestifyme.contestsFeature.ui.ContestsScreen
import com.example.contestifyme.contestsFeature.ui.ContestsViewModel
import com.example.contestifyme.friendsFeature.ui.FriendsScreen
import com.example.contestifyme.friendsFeature.ui.FriendsViewModel
import com.example.contestifyme.problemsFeature.ui.ProblemsScreen
import com.example.contestifyme.problemsFeature.ui.ProblemsViewModel
import com.example.contestifyme.profileFeature.ui.ProfileScreen
import com.example.contestifyme.profileFeature.ui.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContestifyAPP() {
    val navController: NavHostController = rememberNavController()
    Scaffold(
        bottomBar = {
            ContestifyNavigationBar {
                navController.navigate(it.name)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
        ) {

            NavHost(navController = navController, startDestination = PROFILE.name) {

                composable(route = PROFILE.name) {
                    ProfileScreen(viewModel = viewModel(factory = viewModelFactory {
                        initializer {
                            ProfileViewModel(contestifyApplication().profileContainer.profileRepository)
                        }
                    }))
                }
                composable(route = CONTESTS.name) {
                    ContestsScreen(viewModel = viewModel(factory = viewModelFactory {
                        initializer {
                            ContestsViewModel(contestifyApplication().contestsContainer.contestsRepository)
                        }
                    }))
                }
                composable(route = COMPARE.name) {
                    CompareScreen(viewModel = viewModel(factory = viewModelFactory {
                        initializer {
                            CompareViewModel(contestifyApplication().compareContainer.compareRepository)
                        }
                    }))
                }
                composable(route = FRIENDS.name) {
                    FriendsScreen(viewModel = viewModel(factory = viewModelFactory {
                        initializer {
                            FriendsViewModel(contestifyApplication().friendsContainer.friendsRepository)
                        }
                    }))
                }
                composable(route = PROBLEMS.name) {
                    ProblemsScreen(viewModel = viewModel(factory = viewModelFactory {
                        initializer {
                            ProblemsViewModel(contestifyApplication().problemsContainer.problemsRepository)
                        }
                    }))
                }
            }
        }
    }
}
fun CreationExtras.contestifyApplication() : ContestifyApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ContestifyApplication)

@Composable
fun ContestifyNavigationBar(navigateTo: (Screens) -> Unit) {
    val array = listOf(PROFILE, CONTESTS, COMPARE, FRIENDS, PROBLEMS)

    BottomAppBar (
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = SpaceEvenly
        ) {
            repeat(5) {
                IconButton(
                    onClick = {
                        navigateTo(array[it])
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Home"
                    )
                }
            }
        }
    }
}
enum class Screens {
    PROFILE,
    CONTESTS,
    COMPARE,
    FRIENDS,
    PROBLEMS
}