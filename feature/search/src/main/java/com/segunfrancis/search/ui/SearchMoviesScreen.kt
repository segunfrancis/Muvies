@file:OptIn(ExperimentalComposeUiApi::class)

package com.segunfrancis.search.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.segunfrancis.muvies.common.Movies
import com.segunfrancis.muvies.common.components.MuviesItemAll
import com.segunfrancis.muvies.common.theme.Grey400
import com.segunfrancis.muvies.common.theme.MuviesTheme
import com.segunfrancis.muvies.common.theme.MuviesTypography
import com.segunfrancis.muvies.common.theme.buttonColors
import com.segunfrancis.muvies.common.theme.buttonElevation
import com.segunfrancis.muvies.common.theme.outlineTextFieldColors

@Composable
fun SearchMoviesScreen(viewModel: SearchViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(Unit) {
        viewModel.error.collect {
            scaffoldState.snackbarHostState.showSnackbar(
                message = it,
                duration = SnackbarDuration.Long
            )
        }
    }

    SearchMovieContent(
        searchQuery = uiState.searchText,
        uiState = uiState,
        scaffoldState = scaffoldState,
        onAction = { action ->
            when (action) {
                is SearchScreenIntents.OnSearchClick -> viewModel.processIntent(
                    SearchScreenIntents.OnSearchClick(
                        action.searchQuery
                    )
                )

                is SearchScreenIntents.OnSearchFieldChange -> viewModel.processIntent(
                    SearchScreenIntents.OnSearchFieldChange(action.value)
                )

                is SearchScreenIntents.OnTypeClick -> viewModel.processIntent(
                    SearchScreenIntents.OnTypeClick(action.searchType)
                )
            }
        }
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchMovieContent(
    searchQuery: String,
    scaffoldState: ScaffoldState,
    uiState: SearchMoviesUiState,
    onAction: (SearchScreenIntents) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    MuviesTheme {
        Scaffold(scaffoldState = scaffoldState) {
            Surface {
                val keyboardController = LocalSoftwareKeyboardController.current
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            value = searchQuery,
                            onValueChange = { onAction(SearchScreenIntents.OnSearchFieldChange(it)) },
                            singleLine = true,
                            label = { Text("Search") },
                            placeholder = { Text("Search for a movie") },
                            textStyle = MuviesTypography.body1.copy(color = Grey400),
                            colors = outlineTextFieldColors,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(onSearch = {
                                if (searchQuery.isNotBlank()) {
                                    onAction(
                                        SearchScreenIntents.OnSearchClick(
                                            searchQuery
                                        )
                                    )
                                    keyboardController?.hide()
                                }
                            }),
                            trailingIcon = {
                                TextButton(
                                    onClick = { expanded = !expanded },
                                    colors = buttonColors,
                                    elevation = buttonElevation,
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                ) {
                                    Text(
                                        text = uiState.type.name,
                                        color = MaterialTheme.colors.onSurface
                                    )
                                    DropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false }) {
                                        SearchType.values().forEach {
                                            DropdownMenuItem(onClick = {
                                                onAction(
                                                    SearchScreenIntents.OnTypeClick(it)
                                                )
                                                expanded = false
                                            }) {
                                                Text(
                                                    text = it.name,
                                                    color = MaterialTheme.colors.onSurface,
                                                    style = MaterialTheme.typography.subtitle2
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        )

                        if (uiState.movies.isNotEmpty()) {
                            SearchMovieNonEmptyState(movies = uiState.movies)
                        } else {
                            SearchMovieEmptyState()
                        }
                    }
                    if (uiState.isLoading) LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        color = Grey400
                    )
                }
            }
        }
    }
}

@Composable
fun ColumnScope.SearchMovieEmptyState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .weight(1F),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No result found.\nModify your search term and try again",
            style = MuviesTypography.h6,
            textAlign = TextAlign.Center,
            color = Grey400,
            modifier = Modifier
                .wrapContentWidth()
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun SearchMovieNonEmptyState(movies: List<Movies.MoviesResult>) {
    Spacer(modifier = Modifier.height(4.dp))
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(movies) {
            MuviesItemAll(movie = it)
        }
    }
}

sealed class SearchScreenIntents {
    data class OnSearchFieldChange(val value: String) : SearchScreenIntents()
    data class OnSearchClick(val searchQuery: String) : SearchScreenIntents()
    data class OnTypeClick(val searchType: SearchType) : SearchScreenIntents()
}

@Composable
@Preview
fun SearchMovieContentPreview() {
    val movies = mutableListOf<Movies.MoviesResult>()
    repeat(11) {
        movies.add(movie)
    }
    SearchMovieContent(
        searchQuery = "Shang",
        scaffoldState = rememberScaffoldState(),
        uiState = SearchMoviesUiState(isLoading = true)
    ) {

    }
}

val movie = Movies.MoviesResult(
    popularity = 7.5,
    voteAverage = 8.1,
    voteCount = 238,
    video = true,
    posterPath = "",
    backdropPath = "",
    movieCategory = Movies.MoviesResult.MovieCategory.IN_THEATER,
    overview = "This is the overview of the movie",
    title = "Shang chi",
    originalLanguage = "en",
    originalTitle = "Shang shi: Legend of the 10 Rings",
    releaseDate = "12-04-2021",
    genreIds = listOf(1, 2, 3),
    id = 1,
    adult = false
)

enum class SearchType(val value: String) {
    Movies("movie"), TV("tv")
}
