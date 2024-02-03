package com.prafull.contestifyme.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.contestifyme.R
import com.prafull.contestifyme.ContestifyApplication
import com.prafull.contestifyme.features.codeAssistantFeature.ui.CodeAssistanceScreen
import com.prafull.contestifyme.features.codeAssistantFeature.ui.CodeAssistanceViewModel
import com.prafull.contestifyme.features.contestsFeature.ui.ContestsScreen
import com.prafull.contestifyme.features.contestsFeature.ui.ContestsViewModel
import com.prafull.contestifyme.features.friendsFeature.ui.FriendsScreen
import com.prafull.contestifyme.features.friendsFeature.ui.FriendsViewModel
import com.prafull.contestifyme.features.problemsFeature.ui.ProblemsScreen
import com.prafull.contestifyme.features.problemsFeature.ui.ProblemsViewModel
import com.prafull.contestifyme.features.profileFeature.ui.ProfileScreen
import com.prafull.contestifyme.features.profileFeature.ui.ProfileViewModel
import com.prafull.contestifyme.ui.Screens.CODE_ASSISTANCE
import com.prafull.contestifyme.ui.Screens.CONTESTS
import com.prafull.contestifyme.ui.Screens.FRIENDS
import com.prafull.contestifyme.ui.Screens.PROBLEMS
import com.prafull.contestifyme.ui.Screens.PROFILE

@Composable
fun ContestifyAPP (handle: String) {
    val navController: NavHostController = rememberNavController()
    val viewModels = listOf(
        viewModel<ProfileViewModel>(factory = AppViewModelProvider.profileViewModel(handle)),
        viewModel<ContestsViewModel>(factory = AppViewModelProvider.contestVM),
        viewModel<CodeAssistanceViewModel>(factory = AppViewModelProvider.compareVm),
        viewModel<FriendsViewModel>(factory = AppViewModelProvider.friendsVM),
        viewModel<ProblemsViewModel>(factory = AppViewModelProvider.problemsVM)
    )
    ContestifyMainApp(navController = navController, viewModels = viewModels)
}

@Composable
fun ContestifyMainApp(navController: NavHostController, viewModels: List<ViewModel>) {
    var selected by rememberSaveable {
        mutableStateOf(PROFILE.name)
    }
    Scaffold(
        bottomBar = {
            ContestifyNavigationBar {
                if (selected != it.name) {
                    navController.navigate(it.name)
                    selected = it.name
                }
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
        ) {
            NavHost(navController = navController, startDestination = PROFILE.name) {

                composable(route = PROFILE.name) {
                    ProfileScreen(viewModel = viewModels[0] as ProfileViewModel)
                }
                composable(route = CONTESTS.name) {
                    ContestsScreen(viewModel = viewModels[1] as ContestsViewModel)
                }
                composable(route = CODE_ASSISTANCE.name) {
                    CodeAssistanceScreen(viewModel = viewModels[2] as CodeAssistanceViewModel)
                }
                composable(route = FRIENDS.name) {
                    FriendsScreen(viewModel = viewModels[3] as FriendsViewModel)
                }
                composable(route = PROBLEMS.name) {
                    ProblemsScreen(viewModel = viewModels[4] as ProblemsViewModel)
                }
            }
        }
    }
}

fun CreationExtras.contestifyApplication() : ContestifyApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ContestifyApplication)

@Composable
fun ContestifyNavigationBar(navigateTo: (Screens) -> Unit) {
    val array = listOf(PROFILE, CONTESTS, CODE_ASSISTANCE, FRIENDS, PROBLEMS)

    var selectedItem by rememberSaveable { mutableStateOf(0) }
    val unSelectedItems = listOf(
        Pair(R.string.profile, R.drawable.profile),
        Pair(R.string.contest, R.drawable.contest),
        Pair(R.string.code_assistance, R.drawable.baseline_android_24),
        Pair(R.string.friends, R.drawable.friend),
        Pair(R.string.problems, R.drawable.problems)
    )
    val selectedItems = listOf(
        Pair(R.string.profile, R.drawable.profile_filled),
        Pair(R.string.contest, R.drawable.contest),
        Pair(R.string.code_assistance, R.drawable.baseline_android_24),
        Pair(R.string.friends, R.drawable.friends_filled),
        Pair(R.string.problems, R.drawable.problems)
    )

    NavigationBar (
        modifier = Modifier.clip(shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)),
    ) {
        unSelectedItems.forEachIndexed { index, item ->
            NavigationBarItem (
                icon = {
                    Icon(
                        painter = painterResource(
                            if (selectedItem == index) selectedItems[index].second else unSelectedItems[index].second
                        ),
                        contentDescription = stringResource(
                            id = item.first,
                        )
                    )
                },
                label = { Text(text = stringResource(id = item.first)) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navigateTo(array[index])
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors()
            )
        }
    }
}
/**
 *  Enum class for the screens in the app
 * */
enum class Screens {
    PROFILE,
    CONTESTS,
    CODE_ASSISTANCE,
    FRIENDS,
    PROBLEMS
}

/**
 *  ViewModels for the app
 * */
object AppViewModelProvider {
    fun profileViewModel(handle: String): ViewModelProvider.Factory {
        return viewModelFactory {
            initializer {
                ProfileViewModel(
                    profileRepository = contestifyApplication().profileContainer.profileRepository,
                    handle = handle
                )
            }
        }
    }
    val problemsVM = viewModelFactory {
        initializer {
            ProblemsViewModel(contestifyApplication().problemsContainer.problemsRepository)
        }
    }
    val contestVM = viewModelFactory {
        initializer {
            ContestsViewModel(contestifyApplication().contestsContainer.contestsRepository)
        }
    }
    val compareVm = viewModelFactory {
        initializer {
            CodeAssistanceViewModel(/*contestifyApplication().compareContainer.compareRepository*/)
        }
    }
    val friendsVM = viewModelFactory {
        initializer {
            FriendsViewModel(contestifyApplication().friendsContainer.friendsRepository)
        }
    }
}