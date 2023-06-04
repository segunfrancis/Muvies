@file:OptIn(ExperimentalComposeUiApi::class)

package com.czech.muvies.features.search

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.czech.muvies.components.MuviesItemAll
import com.czech.muvies.models.Movies
import com.czech.muvies.theme.Grey400
import com.czech.muvies.theme.Grey700
import com.czech.muvies.theme.MuviesTheme
import com.czech.muvies.theme.MuviesTypography

@Composable
fun SearchMoviesScreen(viewModel: SearchViewModel) {
    val context = LocalContext.current
    val movies by viewModel.searchResponse.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val searchFieldValue by viewModel.searchFieldValue.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.error.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
    SearchMovieContent(
        isLoading = isLoading,
        searchQuery = searchFieldValue,
        movies = movies
    ) { actions ->
        when (actions) {
            is SearchActions.OnSearchClick -> viewModel.searchMovie(actions.searchQuery)
            is SearchActions.OnSearchFieldChange -> viewModel.onSearchFieldChange(actions.value)
        }
    }
}

@Composable
fun SearchMovieContent(
    isLoading: Boolean,
    searchQuery: String,
    movies: List<Movies.MoviesResult>,
    onAction: (SearchActions) -> Unit
) {
    MuviesTheme {
        val keyboardController = LocalSoftwareKeyboardController.current
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    value = searchQuery,
                    onValueChange = { onAction(SearchActions.OnSearchFieldChange(it)) },
                    singleLine = true,
                    label = { Text("Search") },
                    placeholder = { Text("Search for a movie") },
                    textStyle = MuviesTypography.body1.copy(color = Grey400),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Grey400,
                        focusedLabelColor = Grey400,
                        cursorColor = Grey700
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        onAction(
                            SearchActions.OnSearchClick(
                                searchQuery
                            )
                        )
                        keyboardController?.hide()
                    })
                )
                Spacer(modifier = Modifier.height(4.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(all = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(movies) {
                        MuviesItemAll(movie = it)
                    }
                }
            }
            if (isLoading) LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                color = Grey400
            )
        }
    }
}

sealed class SearchActions {
    data class OnSearchFieldChange(val value: String) : SearchActions()
    data class OnSearchClick(val searchQuery: String) : SearchActions()
}

@Composable
@Preview
fun SearchMovieContentPreview() {
    val movies = mutableListOf<Movies.MoviesResult>()
    repeat(11) {
        movies.add(movie)
    }
    SearchMovieContent(isLoading = true, searchQuery = "Shang", movies = movies) {

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
