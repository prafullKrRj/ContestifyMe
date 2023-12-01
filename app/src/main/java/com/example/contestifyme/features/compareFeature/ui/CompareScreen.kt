package com.example.contestifyme.features.compareFeature.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.contestifyme.R
import com.example.contestifyme.commons.ui.SimpleTopAppBar
import com.example.contestifyme.features.compareFeature.ui.components.ComparatorItem
import com.example.contestifyme.ui.AppViewModelProvider

@Composable
fun CompareScreen(
    viewModel: CompareViewModel
) {
    Scaffold(
        topBar = {
            SimpleTopAppBar(
                label = R.string.compare,
            )
        }
    ) { paddingValues ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            ComparatorItem(modifier = Modifier.weight(.5f))
            Divider(Modifier.fillMaxHeight().width(2.dp))
            ComparatorItem(modifier = Modifier.weight(.5f))
        }
    }
}



@Preview(showSystemUi = true)
@Composable
fun CompareScreenPreview() {
    CompareScreen(viewModel = viewModel(factory = AppViewModelProvider.compareVm))
}