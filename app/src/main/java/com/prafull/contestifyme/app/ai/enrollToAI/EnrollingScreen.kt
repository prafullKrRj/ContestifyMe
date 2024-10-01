package com.prafull.contestifyme.app.ai.enrollToAI


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.prafull.contestifyme.R

// UI Component
@Composable
fun EnrollingScreen(viewModel: ApiKeyViewModel, keyAdded: () -> Unit) {
    var apiKey by remember { mutableStateOf("") }
    var isKeyVisible by remember { mutableStateOf(false) }
    val loading by viewModel.loading.collectAsState()
    val keyAdded by viewModel.keyAdded.collectAsState()
    val keyBoardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(key1 = keyAdded) {
        if (keyAdded) {
            keyAdded()
        }
    }
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            Text(
                text = "Enroll to AI",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(16.dp)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (loading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
            }
            Text(
                text = "Enter your API Key", style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = apiKey,
                onValueChange = { apiKey = it },
                label = { Text("API Key") },
                visualTransformation = if (isKeyVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { isKeyVisible = !isKeyVisible }) {
                        Icon(
                            painter = painterResource(id = if (isKeyVisible) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24),
                            contentDescription = "Toggle key visibility"
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        viewModel.saveApiKey(apiKey, context)
                        keyBoardController?.hide()
                    }
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Enter Your Gemini API key is stored securely on your device and is not shared with anyone.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.saveApiKey(apiKey, context) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading
            ) {
                Text("Save API Key")
            }
            /*  FilledTonalButton(modifier = Modifier.fillMaxWidth(), onClick = {
                  navController.navigate(EnrollToAIRoutes.HowToCreateApiKey)
              }) {
                  Text(text = "How to create API Key?")
              }*/
        }

    }

}