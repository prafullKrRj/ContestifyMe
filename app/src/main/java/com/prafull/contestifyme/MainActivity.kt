package com.prafull.contestifyme

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prafull.contestifyme.app.ContestifyAPP
import com.prafull.contestifyme.onboard.OnBoardingScreen
import com.prafull.contestifyme.ui.theme.ContestifyMeTheme
import com.prafull.contestifyme.utils.managers.SharedPrefManager
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ContestifyMeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val startDestination =
                        if (SharedPrefManager(this).isLoggedIn()) Routes.ContestifyApp else Routes.OnBoarding

                    NavHost(navController = navController, startDestination = startDestination) {
                        composable<Routes.ContestifyApp> {
                            ContestifyAPP()
                        }
                        composable<Routes.OnBoarding> {
                            OnBoardingScreen(viewModel = koinViewModel(), navController)
                        }
                    }
                }
            }
        }
    }
}

sealed interface Routes {

    @Serializable
    data object ContestifyApp : Routes

    @Serializable
    data object OnBoarding : Routes
}

fun NavController.navigateAndClearBackStack(route: Any) {
    this.navigate(route)
}

fun NavController.goBackStack() {
    if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        popBackStack()
    }
}