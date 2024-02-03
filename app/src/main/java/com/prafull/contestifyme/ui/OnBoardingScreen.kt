package com.prafull.contestifyme.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.contestifyme.R
import com.prafull.contestifyme.features.friendsFeature.ui.LoadingScreen

@Composable
fun OnBoardingScreen (viewModel: OnBoardingVM, sendLoggedInUser: (String) -> Unit) {
    val scrollState = rememberScrollState()
    var handle by rememberSaveable {
        mutableStateOf("")
    }
    val state = viewModel.onBoardState.collectAsState()
    var buttonEnabled by rememberSaveable {
        mutableStateOf(true)
    }
    when (state.value) {
        is OnBoardingState.Loading -> {
            LoadingScreen()
            buttonEnabled = false
        }
        is OnBoardingState.Success -> {
            sendLoggedInUser(handle)
            buttonEnabled = false
        }
        is OnBoardingState.Error -> {
            buttonEnabled = true
        }
        else -> {

        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState, enabled = true)
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ContestifyImage(modifier = Modifier)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "CONTESTIFY ME",
            modifier = Modifier,
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        HandleInput(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
        ) {
            handle = it
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.saveUser(handle)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            enabled = buttonEnabled
        ) {
            Text(text = "Log In")
        }
    }
}
@Composable
fun HandleInput(modifier: Modifier, updateText: (String) -> Unit) {
    var text by rememberSaveable {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = modifier,
        value = text,
        onValueChange = {
            text = it
            updateText(it)
        },
        label = { Text("Handle") },
        shape = RoundedCornerShape(8.dp),
        singleLine = true
    )
}

@Composable
fun ContestifyImage(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.very_large_logo),
        contentDescription = "app logo",
        modifier = modifier
    )
}