@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.contestifyme.features.problemsFeature.ui

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Box
import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.contestifyme.R
import com.example.contestifyme.features.problemsFeature.data.local.entities.ProblemsEntity
import com.example.contestifyme.features.problemsFeature.ui.components.SelectionChip
import com.example.contestifyme.features.problemsFeature.ui.components.sortComponents.SortDialog
import com.example.contestifyme.features.problemsFeature.ui.components.tagsComponent.TagsDialog
import okhttp3.internal.filterList

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProblemsScreen(viewModel: ProblemsViewModel) {
    val state by viewModel.problemsUiState.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }


    var selectedTags by remember { mutableStateOf(emptyList<String>()) }    // List of selected tags
    var sortType by rememberSaveable {                                      // Sort Type (Rating)
        mutableStateOf(Pair(0, 0))
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) {
                // TODO: Add snackbar
            }
        },
        topBar = {
            ProblemsTopBar()
        }
    ) { paddingValues ->
        ProblemsUI(
            list = state.entity.filter {
                if (sortType.first != 0 && sortType.second != 0) {                      // Filter List based on Rating
                    it.rating>=sortType.first && it.rating <= sortType.second
                } else {
                    true
                }
            }.filterList {                                              // Filter List based on Tags
                if (selectedTags.isNotEmpty()) {
                    this.tags.containsAll(selectedTags)
                } else {
                    true
                }
            },                          // Sort List based on Rating
            modifier = Modifier.padding(paddingValues = paddingValues),
            ratingSelected = {
                       sortType = it
            }, selectedTags = selectedTags,
            previousType = sortType,
            updateList = {
                selectedTags = it
            }
        )
    }
}
@Composable
fun ProblemsUI(
    list: List<ProblemsEntity>,
    modifier: Modifier,
    ratingSelected: (Pair<Int, Int>) -> Unit,    // Sort Type (Rating)
    selectedTags: List<String>,                 // List of selected tags
    updateList: (List<String>) -> Unit,         // Update List based on Tags
    previousType: Pair<Int, Int>                // Previous Sort Type
) {

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
        if (list.isEmpty()) {
            EmptyListBox()
        } else {
            ProblemsList(list = list)
        }
    }
}

@Composable
fun EmptyListBox() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier,
            text = "No Problem Found",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Start,
        )
    }
}
@Composable
fun ProblemsList(list: List<ProblemsEntity>) {
    LazyColumn (modifier = Modifier.padding(horizontal = 12.dp)) {
        list.forEach {
            item {
                ProblemItemCard(it)
            }
        }
    }
}
@Composable
fun TagsSection (
    selectedTags: List<String>,
    sortType: (Pair<Int, Int>) -> Unit,
    updateList: (List<String>) -> Unit = {},
    previousType: Pair<Int, Int>
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