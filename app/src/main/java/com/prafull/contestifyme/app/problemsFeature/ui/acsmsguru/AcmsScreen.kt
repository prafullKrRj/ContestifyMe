package com.prafull.contestifyme.app.problemsFeature.ui.acsmsguru

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafull.contestifyme.app.App
import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.problemsFeature.domain.model.acmsguru.AcmsguruDto
import com.prafull.contestifyme.app.problemsFeature.ui.ProblemItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AcmsScreen(viewModel: AcmsGuruViewModel, navController: NavController) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AcmsGuru") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state) {
                is BaseClass.Loading -> {
                    CircularProgressIndicator()
                }

                is BaseClass.Success -> {
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 12.dp)
                    ) {
                        items(
                            (state as BaseClass.Success<AcmsguruDto>).data
                                .result.problems
                        ) {
                            ProblemItemCard(entity = it.toEntity()) {
                                navController.navigate(
                                    App.WebViewScreen(
                                        url = "https://codeforces.com/problemsets/acmsguru/problem/99999/${it.index}",
                                        heading = it.name
                                    )
                                )
                            }
                        }
                    }
                }

                is BaseClass.Error -> {
                    Text(text = "Error: ${(state as BaseClass.Error).exception.message}")
                    Button(onClick = { viewModel.getAcmsGuruProblems() }) {
                        Text(text = "Retry")
                    }
                }
            }
        }
    }
}