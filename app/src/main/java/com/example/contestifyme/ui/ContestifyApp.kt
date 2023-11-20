package com.example.contestifyme.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.contestifyme.ContestifyApplication
import com.example.contestifyme.R
import com.example.contestifyme.features.compareFeature.ui.CompareScreen
import com.example.contestifyme.features.compareFeature.ui.CompareViewModel
import com.example.contestifyme.features.contestsFeature.ui.ContestsScreen
import com.example.contestifyme.features.contestsFeature.ui.ContestsViewModel
import com.example.contestifyme.features.friendsFeature.ui.FriendsScreen
import com.example.contestifyme.features.friendsFeature.ui.FriendsViewModel
import com.example.contestifyme.features.problemsFeature.ui.ProblemsScreen
import com.example.contestifyme.features.problemsFeature.ui.ProblemsViewModel
import com.example.contestifyme.features.profileFeature.ui.ProfileScreen
import com.example.contestifyme.features.profileFeature.ui.ProfileViewModel
import com.example.contestifyme.ui.Screens.COMPARE
import com.example.contestifyme.ui.Screens.CONTESTS
import com.example.contestifyme.ui.Screens.FRIENDS
import com.example.contestifyme.ui.Screens.PROBLEMS
import com.example.contestifyme.ui.Screens.PROFILE
import kotlinx.coroutines.delay

@Composable
fun ContestifyAPP (viewModel: OnBoardingVM) {
    val navController: NavHostController = rememberNavController()
    val viewModelState: OnBoardingState by viewModel.state.collectAsState()
    var showBox by rememberSaveable {
        mutableStateOf(true)
    }
    LaunchedEffect(Unit) {
        delay(1500)
        showBox = false
    }
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visible = true
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (viewModelState.users.isEmpty()) {
            OnBoardingScreen(
                viewModel = viewModel,
            )
        } else {
            ContestifyMainApp(navController, viewModelState.users[0].handle)
        }
        if (showBox) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                , contentAlignment = Alignment.Center) {
                ContestifyImage(modifier = Modifier)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContestifyMainApp(navController: NavHostController, handle: String) {
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
                    ProfileScreen(viewModel = viewModel(factory = AppViewModelProvider.profileViewModel(handle)), handle = handle)
                }
                composable(route = CONTESTS.name) {
                    ContestsScreen(viewModel = viewModel(factory = AppViewModelProvider.contestVM))
                }
                composable(route = COMPARE.name) {
                    CompareScreen(viewModel = viewModel(factory = AppViewModelProvider.compareVm))
                }
                composable(route = FRIENDS.name) {
                    FriendsScreen(viewModel = viewModel(factory = AppViewModelProvider.friendsVM))
                }
                composable(route = PROBLEMS.name) {
                    ProblemsScreen(viewModel = viewModel(factory = AppViewModelProvider.problemsVM))
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

    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf(
        Pair(R.string.profile, R.drawable.profile),
        Pair(R.string.contest, R.drawable.contest),
        Pair(R.string.compare, R.drawable.compare),
        Pair(R.string.friends, R.drawable.friends),
        Pair(R.string.problems, R.drawable.problems)
    )

    NavigationBar (
        modifier = Modifier.clip(shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)),
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem (
                icon = { Icon(
                    painter = painterResource(id = item.second),
                    contentDescription = stringResource(
                        id = item.first,
                    )
                ) },
                label = { Text(text = stringResource(id = item.first)) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navigateTo(array[index])
                },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors()
            )
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
            CompareViewModel(contestifyApplication().compareContainer.compareRepository)
        }
    }
    val friendsVM = viewModelFactory {
        initializer {
            FriendsViewModel(contestifyApplication().friendsContainer.friendsRepository)
        }
    }
}