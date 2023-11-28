package com.example.contestifyme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.contestifyme.ui.ContestifyAPP
import com.example.contestifyme.ui.OnBoardingVM
import com.example.contestifyme.ui.contestifyApplication
import com.example.contestifyme.ui.theme.ContestifyMeTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContestifyMeTheme(darkTheme = true) {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    ContestifyAPP(
                        viewModel = viewModel(factory = viewModelFactory {
                            initializer {
                                OnBoardingVM(onBoardingRepository = contestifyApplication().onBoardContainer.onBoardRepository)
                            }
                        })
                    )
                }
            }
        }
    }
}
