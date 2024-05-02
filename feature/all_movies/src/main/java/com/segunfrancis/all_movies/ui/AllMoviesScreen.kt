package com.segunfrancis.all_movies.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.segunfrancis.muvies.common.Movies
import com.segunfrancis.muvies.common.components.MuviesItemAll
import com.segunfrancis.muvies.common.theme.Grey400

@Composable
fun AllMoviesScreen(
    modifier: Modifier = Modifier,
    onItemClick: (Movies.MoviesResult) -> Unit = {},
    movies: LazyPagingItems<Movies.MoviesResult>
) {

    Surface(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(movies) { moviesResult ->
                moviesResult?.let { MuviesItemAll(movie = it, onItemClick = onItemClick) }
            }
            if (movies.loadState.append == LoadState.Loading) {
                item {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        color = Grey400
                    )
                }
            }
        }
    }
}
