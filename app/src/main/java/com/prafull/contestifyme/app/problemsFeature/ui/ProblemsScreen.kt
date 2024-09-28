@file:OptIn(ExperimentalMaterial3Api::class)

package com.prafull.contestifyme.app.problemsFeature.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafull.contestifyme.R
import com.prafull.contestifyme.app.App
import com.prafull.contestifyme.app.commons.BaseClass
import com.prafull.contestifyme.app.commons.ui.ErrorScreen
import com.prafull.contestifyme.app.commons.ui.SimpleTopAppBar
import com.prafull.contestifyme.app.problemsFeature.data.local.entities.ProblemsEntity
import com.prafull.contestifyme.app.problemsFeature.ui.components.SelectionChip
import com.prafull.contestifyme.app.problemsFeature.ui.components.sortComponents.SortDialog
import com.prafull.contestifyme.app.problemsFeature.ui.components.tagsComponent.TagsDialog
import okhttp3.internal.filterList

@Composable
fun ProblemsMain(viewModel: ProblemsViewModel, navController: NavController) {

    val state by viewModel.uiState.collectAsState()
    var selectedTags by rememberSaveable { mutableStateOf(emptyList<String>()) }    // List of selected tags
    var ratingRanges by rememberSaveable {                                      // Sort Type (Rating)
        mutableStateOf(Pair(0, 0))
    }
    Scaffold(topBar = {
        SimpleTopAppBar(
            label = R.string.problems, navIcon = Icons.Default.Menu, navIconClicked = {

            }
        )
    }) { paddingValues ->
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state) {
                is BaseClass.Error -> {
                    ErrorScreen {
                        viewModel.getProblemsFromInternet()
                    }
                }

                BaseClass.Loading -> {
                    CircularProgressIndicator()
                }

                is BaseClass.Success -> {
                    ProblemsUI(list = (state as BaseClass.Success<ProblemState>).data.entity.filter {
                        if (ratingRanges.first != 0 && ratingRanges.second != 0) {
                            it.rating >= ratingRanges.first && it.rating <= ratingRanges.second
                        } else {
                            true
                        }
                    }.filterList {
                        if (selectedTags.isNotEmpty()) {
                            this.tags.containsAll(selectedTags)
                        } else {
                            true
                        }
                    },
                        modifier = Modifier.padding(paddingValues = paddingValues),
                        ratingSelected = {
                            ratingRanges = it
                        },
                        selectedTags = selectedTags,
                        previousType = ratingRanges,
                        updateList = {
                            selectedTags = it
                        },
                        navController = navController
                    )
                }
            }
        }

    }
}

@Composable
fun ProblemsUI(
    list: List<ProblemsEntity>,
    modifier: Modifier,
    ratingSelected: (Pair<Int, Int>) -> Unit,    // Sort Type (Rating)
    selectedTags: List<String>,                 // List of selected tags
    updateList: (List<String>) -> Unit,         // Update List based on Tags
    previousType: Pair<Int, Int>,// Previous Sort Type
    navController: NavController,
) {
    val focusManager = LocalFocusManager.current
    var query by rememberSaveable {
        mutableStateOf("")
    }
    var isSearched by rememberSaveable {
        mutableStateOf(false)
    }
    var isSortSelected by rememberSaveable {
        mutableStateOf(false)
    }
    var sortType by rememberSaveable {
        mutableStateOf(0)
    }
    Column(modifier.fillMaxSize()) {
        SearchBar(
            query = query,
            isSearched = isSearched,
            onValueChange = {
                query = it
                isSearched = true
            }, cancelSearch = {
                isSearched = false
                query = ""
                focusManager.clearFocus()
            })
        Spacer(modifier = Modifier.padding(vertical = 2.dp))
        TagsSection(selectedTags, previousType = previousType, sortType = {
            ratingSelected(it)
        }, updateList = {
            updateList(it)
        }, onAscSort = {
            isSortSelected = true
            sortType = 0
        }, onDescSort = {
            isSortSelected = true
            sortType = 1
        }, removeSorting = {
            isSortSelected = false
        })
        SelectedTagsRow(selectedTags = selectedTags) { removedTag ->
            updateList(selectedTags.filterList {
                this != removedTag
            })
        }
        Spacer(modifier = Modifier.padding(vertical = 2.dp))
        if (list.isEmpty()) {
            EmptyListBox()
        } else {
            ProblemsList(list = list.filter {
                it.name.contains(query, ignoreCase = true) || it.index.contains(
                    query,
                    ignoreCase = true
                )
            }.sortedBy {
                if (isSortSelected) {
                    if (sortType == 0) {
                        it.rating
                    } else {
                        -it.rating
                    }
                } else {
                    -it.unique.split("|").first().toInt()
                }
            }, navController)
        }
    }
}

@Composable
fun SelectedTagsRow(selectedTags: List<String>, removeTag: (String) -> Unit) {
    LazyRow(
        modifier = Modifier,
        contentPadding = PaddingValues(horizontal = 8.dp) // 8.dp between each item
    ) {
        items(items = selectedTags) { item ->
            AssistChip(onClick = {
                removeTag(item)
            }, label = {
                Text(item)
            }, leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Remove Tag",
                    modifier = Modifier.size(AssistChipDefaults.IconSize)
                )
            }, modifier = Modifier.padding(end = 4.dp)
            )
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
fun ProblemsList(list: List<ProblemsEntity>, navController: NavController) {
    LazyColumn(modifier = Modifier.padding(horizontal = 12.dp)) {
        list.forEach {
            item {
                ProblemItemCard(it) {
                    val item = it.unique.split("|")
                    navController.navigate(
                        App.WebViewScreen(
                            url = "https://codeforces.com/problemset/problem/${item[0]}/${item[1]}",
                            heading = it.index + ". " + it.name
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun TagsSection(
    selectedTags: List<String>,
    sortType: (Pair<Int, Int>) -> Unit,
    updateList: (List<String>) -> Unit = {},
    previousType: Pair<Int, Int>,
    onAscSort: () -> Unit = {},
    onDescSort: () -> Unit = {},
    removeSorting: () -> Unit
) {
    var tagsSelected by remember {
        mutableStateOf(false)
    }
    var rangeSelected by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            SelectionChip(selected = tagsSelected, title = "Tags", icon = R.drawable.filter_icon) {
                tagsSelected = true
            }
            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
            SelectionChip(selected = rangeSelected, title = "Sort", icon = R.drawable.sort_icon) {
                rangeSelected = true
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            IconButton(onClick = removeSorting) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_access_time_filled_24),
                    contentDescription = "Clear"
                )
            }
            IconButton(onClick = onDescSort) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_arrow_upward_24),
                    contentDescription = "Clear"
                )
            }
            IconButton(onClick = onAscSort) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_arrow_downward_24),
                    contentDescription = "Clear"
                )
            }
        }
    }
    if (tagsSelected) {
        TagsDialog(selectedTags = selectedTags, updateList = {
            tagsSelected = false
            updateList(it)
            println("one$it")
        })
    }
    if (rangeSelected) {
        SortDialog(previousType) { rating ->
            rangeSelected = false
            sortType(rating)
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    isSearched: Boolean,
    onValueChange: (String) -> Unit,
    cancelSearch: () -> Unit
) {
    OutlinedTextField(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
        value = query,
        onValueChange = onValueChange,
        label = { Text("Search") },
        singleLine = true,
        shape = RoundedCornerShape(50),
        trailingIcon = {
            if (isSearched) {
                IconButton(onClick = cancelSearch) {
                    Icon(
                        imageVector = Icons.Filled.Clear, contentDescription = "Clear"
                    )
                }
            }
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search, contentDescription = "Search Icon"
            )
        })
}

@Composable
fun ProblemItemCard(entity: ProblemsEntity, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                onClick()
            },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            Text(text = entity.index + ". " + entity.name)
            Text(text = "${entity.rating}", fontWeight = FontWeight.Light)
        }
    }
}