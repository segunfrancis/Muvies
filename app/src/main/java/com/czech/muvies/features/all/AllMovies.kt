package com.czech.muvies.features.all

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.czech.muvies.components.MuviesItemAll
import com.czech.muvies.components.movie
import com.czech.muvies.models.Movies

@Composable
fun AllMoviesScreen(
    modifier: Modifier = Modifier,
    onItemClick: (Movies.MoviesResult) -> Unit = {},
    movies: LazyPagingItems<Movies.MoviesResult>
) {

    Surface(modifier = modifier.fillMaxSize()) {
        val listState = rememberLazyListState()
        LazyColumn(
            modifier = Modifier,
            state = listState,
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(movies) { moviesResult ->
                if (moviesResult != null)
                    MuviesItemAll(movie = moviesResult, onItemClick = onItemClick)
                else {
                    // todo: display loading placeholder
                }
            }
        }
    }
}

@Composable
@Preview
fun AllMoviesScreenPreview() {
    //AllMoviesScreen(movies = listOf(movie).plus(movie))
}
