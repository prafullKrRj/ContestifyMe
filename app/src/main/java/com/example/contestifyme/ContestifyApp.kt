package com.example.contestifyme

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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.contestifyme.Screens.PROFILE
import com.example.contestifyme.Screens.COMPARE
import com.example.contestifyme.Screens.CONTESTS
import com.example.contestifyme.Screens.FRIENDS
import com.example.contestifyme.Screens.PROBLEMS

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

                }
                composable(route = CONTESTS.name) {

                }
                composable(route = COMPARE.name) {

                }
                composable(route = FRIENDS.name) {

                }
                composable(route = PROBLEMS.name) {

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
enum class Screens {
    PROFILE,
    CONTESTS,
    COMPARE,
    FRIENDS,
    PROBLEMS
}