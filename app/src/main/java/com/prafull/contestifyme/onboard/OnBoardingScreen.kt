package com.prafull.contestifyme.onboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafull.contestifyme.R
import com.prafull.contestifyme.Routes
import com.prafull.contestifyme.app.commons.BaseClass
import retrofit2.HttpException
import java.io.IOException

@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel,
    navController: NavController,
) {
    val scrollState = rememberScrollState()
    val loginState by viewModel.loginState.collectAsState()
    val succeeded by viewModel.succeeded.collectAsState()

    LaunchedEffect(key1 = succeeded) {
        if (succeeded) {
            navController.navigate(Routes.ContestifyApp)
        }
    }

    var handle by rememberSaveable {
        mutableStateOf("")
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(value = handle, onValueChange = {
                handle = it
            }, label = { Text("Enter your handle") },
                modifier = Modifier.padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Person, contentDescription = "person icon")
                },
                trailingIcon = {
                    IconButton(onClick = { viewModel.login(handle) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Send,
                            contentDescription = "send icon"
                        )
                    }
                },
                singleLine = true,
                keyboardActions = KeyboardActions(onSend = {
                    viewModel.login(handle)
                }),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Send
                ),
                enabled = loginState !is OnBoardingState.Loading
            )
            if (loginState is OnBoardingState.Error) {
                when ((loginState as BaseClass.Error).exception) {
                    UserNotFoundException() -> {
                        Text(
                            "* User not found. Enter valid handle",
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    is IOException -> {
                        Text(
                            text = "No internet connection",
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    is HttpException -> {
                        Text(
                            text = "User Not Found",
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    else -> {
                        Text(
                            "Error",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
        if (loginState is OnBoardingState.Loading) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun ContestifyImage(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.very_large_logo),
        contentDescription = "app logo",
        modifier = modifier
    )
}