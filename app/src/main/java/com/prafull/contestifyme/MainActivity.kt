package com.prafull.contestifyme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prafull.contestifyme.managers.SharedPrefManager
import com.prafull.contestifyme.ui.ContestifyAPP
import com.prafull.contestifyme.ui.OnBoardingScreen
import com.prafull.contestifyme.ui.OnBoardingVM
import com.prafull.contestifyme.ui.contestifyApplication
import com.prafull.contestifyme.ui.theme.ContestifyMeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ContestifyMeTheme(darkTheme = true) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    val navController = rememberNavController()
                    val startDestination =
                        if (SharedPrefManager(this).isLoggedIn()) "contestifyApp" else "onBoarding"
                    NavHost(navController = navController, startDestination = startDestination) {
                        composable("contestifyApp") {
                            ContestifyAPP()
                        }
                        composable("onBoarding") {
                            OnBoardingScreen(viewModel = viewModel(factory = viewModelFactory {
                                initializer {
                                    OnBoardingVM(onBoardingRepository = contestifyApplication().onBoardContainer.onBoardRepository)
                                }
                            })) { handle ->
                                SharedPrefManager(context).setLoggedIn(true)
                                SharedPrefManager(context).setLoginUserHandle(handle)
                                navController.goBackStack()
                                navController.navigate("contestifyApp")
                            }
                        }
                    }
                }
            }
        }
    }
}

fun NavController.goBackStack() {
    if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        popBackStack()
    }
}