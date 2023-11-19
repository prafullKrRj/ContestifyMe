package com.example.contestifyme.features.problemsFeature.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProblemsScreen(viewModel: ProblemsViewModel) {
    val state by viewModel.problemsUiState.collectAsState()
    ProblemsUI(problems = state)
}

@Composable
fun ProblemsUI(problems: ProblemState) {
    LazyColumn (Modifier.padding(16.dp)) {
        problems.entity.forEach {
            item {
                Text(text = it.name)
                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }
}