@file:OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@file:Suppress("UNUSED_EXPRESSION")

package com.example.contestifyme.features.problemsFeature.ui

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.contestifyme.R
import com.example.contestifyme.features.problemsFeature.data.local.entities.ProblemsEntity
import com.example.contestifyme.features.problemsFeature.problemsConstants.ProblemsConstants
import com.example.contestifyme.features.problemsFeature.ui.components.SelectionChip
import com.example.contestifyme.features.problemsFeature.ui.components.sortComponents.SortDialog
import com.example.contestifyme.features.problemsFeature.ui.components.tagsComponent.TagsDialog
import kotlinx.coroutines.delay

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProblemsScreen(viewModel: ProblemsViewModel) {
    val state by viewModel.problemsUiState.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var shouldDelay by rememberSaveable { mutableStateOf(true) }
    var selectedTags by remember { mutableStateOf(ProblemsConstants.tags) }
    var sortType by rememberSaveable {
        mutableStateOf(0)
    }
    LaunchedEffect(Unit) {
        delay(1500) // Delay for 2000 milliseconds (2 seconds)
        shouldDelay = false // Set the flag to false after the delay
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) {

            }
        },
        topBar = {
            ProblemsTopBar()
        }
    ) { paddingValues ->
        ProblemsUI(
            list = state.entity.filter {
                if (selectedTags.isNotEmpty()) {
                    it.tags.contains("dp")
                } else {
                    true
                }
                if (sortType != 0) {
                    it.rating == sortType
                } else {
                    true
                }
            },
            modifier = Modifier.padding(paddingValues = paddingValues),
            ratingSelected = {
                       sortType = it
            }, selectedTags = selectedTags,
            previousType = sortType,
            updateList = {
                selectedTags = it
            }
        )
        if (shouldDelay) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}
@Composable
fun ProblemsUI(
    list: List<ProblemsEntity>,
    modifier: Modifier,
    ratingSelected: (Int) -> Unit,
    selectedTags: List<String>,
    updateList: (List<String>) -> Unit,
    previousType: Int
) {
    //println(list)
    Column (modifier.fillMaxSize()){
        SearchBar()
        Spacer(modifier = Modifier.padding(vertical = 2.dp))
        TagsSection(
            selectedTags,
            previousType = previousType,
            sortType = {
                       ratingSelected(it)
            },
            updateList = {
                updateList(it)
            }
        )
        Spacer(modifier = Modifier.padding(vertical = 2.dp))
        LazyColumn (modifier = Modifier.padding(horizontal = 12.dp)) {
            list.forEach {
                item {
                    ProblemItemCard(it)
                }
            }
        }
    }
}

@Composable
fun TagsSection (
    selectedTags: List<String>,
    sortType: (Int) -> Unit,
    updateList: (List<String>) -> Unit = {},
    previousType: Int
) {
    var tagsSelected by remember {
         mutableStateOf(false)
    }
    var sortSelected by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        SelectionChip(selected = tagsSelected, title = "Tags", icon = R.drawable.filter_icon) {
            tagsSelected = true
        }
        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
        SelectionChip(selected = sortSelected, title = "Sort", icon = R.drawable.sort_icon) {
            sortSelected = true
        }
    }
    if (tagsSelected) {
        TagsDialog(selectedTags = selectedTags, updateList = {
            tagsSelected = false
            updateList(it)
            println("one$it")
        })
    }
    if (sortSelected) {
        SortDialog(previousType) {rating ->
            sortSelected = false
            sortType(rating)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    var text by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = text,
        onValueChange = { text = it },
        label = { Text("Search") },
        singleLine = true,
        shape = RoundedCornerShape(50),
        trailingIcon = {
            if (text.isNotEmpty()) {
                IconButton(onClick = { text = "" }) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Clear"
                    )
                }
            }
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search Icon"
            )
        }
    )
}
@Composable
fun ProblemItemCard(entity: ProblemsEntity) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { },
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            Text(text = entity.index+". "+entity.name)
            Text(text = "${entity.rating}", fontWeight = FontWeight.Light)
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProblemsTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Problems",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu"
                )
            }
        },
        actions = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Favorite"
                )
            }
        }
    )
}