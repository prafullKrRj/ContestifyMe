@file:OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)

package com.example.contestifyme.features.problemsFeature.ui

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.contestifyme.R
import com.example.contestifyme.features.problemsFeature.data.local.entities.ProblemsEntity
import com.example.contestifyme.features.problemsFeature.problemsConstants.ProblemsConstants
import kotlinx.coroutines.delay

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProblemsScreen(viewModel: ProblemsViewModel) {
    val state by viewModel.problemsUiState.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var shouldDelay by rememberSaveable { mutableStateOf(true) }
    var selectedTags by remember { mutableStateOf(listOf<String>()) }
    var tags by rememberSaveable {
        mutableStateOf(listOf(""))
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
        ProblemsUI(viewModel = viewModel, list = state.entity.filter {
            if (selectedTags.isNotEmpty()) {
                it.tags.containsAll(selectedTags)
            } else {
                true
            }
        }, modifier = Modifier.padding(paddingValues = paddingValues), selectedTags = selectedTags, sortType = {

        }) {
            selectedTags = it
            tags.plus(it)
        }
        if (shouldDelay) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}
@Composable
fun ProblemsUI(viewModel: ProblemsViewModel, list: List<ProblemsEntity>, modifier: Modifier, sortType: (String) -> Unit, selectedTags: List<String>, updateList: (List<String>) -> Unit) {
    Column (modifier.fillMaxSize()){
        SearchBar()
        Spacer(modifier = Modifier.padding(vertical = 2.dp))
        TagsSection(viewModel, selectedTags, sortType = {

        }) {
            updateList(it)
        }
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
fun TagsSection (viewModel: ProblemsViewModel, selectedTags: List<String>, sortType: (String) -> Unit, updateList: (List<String>) -> Unit = {}) {
    var tagsSelected by remember {
         mutableStateOf(false)
    }
    var sortSelected by remember {
        mutableStateOf(false)
    }
    Column (modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .animateContentSize(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMediumLow
            )
        )
    ) {
        Row{
            SelectionChip(false, title = "Tags", icon = R.drawable.filter_icon) {
                tagsSelected = !tagsSelected
            }
            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
            SelectionChip(false, title = "Sort", icon = R.drawable.sort_icon) {
                sortSelected = !sortSelected
            }
        }
        if (tagsSelected) {
            TagsSelectionWindow(selectedTags) {
                updateList(it)
            }
        }
        if (sortSelected) {
            SortDialog("none") {
                sortSelected = false
            }
        }
    }
}

@Composable
fun SortDialog(previousType: String, sortType: (String) -> Unit) {
    var selected by remember { mutableStateOf(previousType) }
    AlertDialog(
        onDismissRequest = {
                       sortType(previousType)
        },
        title = { Text(text = "Sort By") },
        text = {
            Column {
                Row (
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Checkbox(checked = selected == "dsc", onCheckedChange = {
                        selected = "dsc"
                    })
                }
                Row (
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Checkbox(checked = selected == "asc", onCheckedChange = {
                        selected = "asc"
                    })
                }
                Row (
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Checkbox(checked = selected == "none", onCheckedChange = {
                        selected = "none"
                    })
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                sortType(selected)
            }) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                sortType("none")
            }) {
                Text(text = "Cancel")
            }
        }
    )
}
@ExperimentalMaterial3Api
@Composable
fun SelectionChip(selected: Boolean, title: String, icon: Int, onClick: () -> Unit) {
    var innerSelected by remember { mutableStateOf(false) }
    FilterChip(
        selected = selected,
        onClick = {
            innerSelected = !innerSelected
            onClick()
        },
        label = {
            Text(title)
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "Done",
                modifier = Modifier.size(FilterChipDefaults.IconSize)
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = Color.Transparent,
            selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    )
}
@Composable
fun TagsSelectionWindow(list: List<String>, selectedTags: (List<String>) -> Unit) {
    var tagsList by rememberSaveable {
        mutableStateOf(list)
    }
    val scrollState = rememberScrollState()
    ElevatedCard (
        Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(8.dp))
            .verticalScroll(scrollState)
            .padding(4.dp)
    ) {
            FlowRow (Modifier.padding(8.dp)) {
                ProblemsConstants.tags.forEach {
                    FilterChip(
                        modifier = Modifier.padding(end = 4.dp),
                        selected = tagsList.contains(it),
                        onClick = {
                            tagsList = if (tagsList.contains(it)) {
                                tagsList.filter { tag -> tag != it }
                            } else {
                                tagsList + it
                            }
                            selectedTags(tagsList)
                        },
                        label = {
                            Text(it)
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary,
                            selectedContainerColor = MaterialTheme.colorScheme.inversePrimary
                        )
                    )
                }
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