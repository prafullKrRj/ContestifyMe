package com.example.contestifyme.ui

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily.Companion.Cursive
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.contestifyme.R
import kotlinx.coroutines.launch

@Composable
fun OnBoardingScreen (viewModel: OnBoardingVM) {
    val scrollState = rememberScrollState()
    var handle by rememberSaveable {
        mutableStateOf("")
    }
    val coroutineScope = rememberCoroutineScope()
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
            fontFamily = Cursive,
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
        SaveButton (modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
        ) {
            viewModel.saveUser(handle)
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
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
        colors = TextFieldDefaults.textFieldColors(),
        singleLine = true
    )
}
@Composable
fun SaveButton(modifier: Modifier, checkAndSaveUser: () -> Unit) {
    Button(
        onClick = {
                  checkAndSaveUser()
        },
        modifier = modifier
    ) {
        Text(text = "Text")
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