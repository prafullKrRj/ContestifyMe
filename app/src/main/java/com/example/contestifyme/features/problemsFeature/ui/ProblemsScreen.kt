package com.example.contestifyme.features.problemsFeature.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Modifier
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.contestifyme.features.problemsFeature.model.ProblemsDto

@Composable
fun ProblemsScreen(viewModel: ProblemsViewModel) {
    when (val uiState = viewModel.problemsUiState) {
        is ProblemsUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is ProblemsUiState.Success -> {
            ProblemsUI(problems = uiState.data)
        }
        is ProblemsUiState.Error -> {
            Text(text = "Error")
        }
    }
}

@Composable
fun ProblemsUI(problems: ProblemsDto) {
    LazyColumn (Modifier.padding(16.dp)) {
        problems.result.problems.forEach {
            item {
                Text(text = it.name)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}