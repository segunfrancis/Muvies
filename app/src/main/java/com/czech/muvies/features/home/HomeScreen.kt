package com.czech.muvies.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.czech.muvies.components.CategoryHeader
import com.czech.muvies.components.MuviesItem
import com.czech.muvies.components.movie
import com.czech.muvies.models.Movies

@Composable
fun HomeScreen(
    movies: AllMovies,
    modifier: Modifier = Modifier,
    onMovieItemClick: (Movies.MoviesResult) -> Unit = {},
    onSeeAllClick: (Movies.MoviesResult.MovieCategory) -> Unit = {}
) {
    Surface(modifier = modifier.fillMaxSize()) {
        LazyColumn {
            movies.movies.groupBy { it.movieCategory }.forEach { (key, value) ->
                key?.let {
                    item {
                        CategoryHeader(category = it, onClick = {
                            onSeeAllClick(it)
                        })
                    }
                }
                item {
                    MuviesRow(
                        muvies = value,
                        onMovieItemClick = { onMovieItemClick.invoke(it) })
                }
            }
        }
    }
}

@Composable
fun MuviesRow(
    muvies: List<Movies.MoviesResult>,
    onMovieItemClick: (Movies.MoviesResult) -> Unit = {}
) {
    LazyRow(
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = muvies) {
            MuviesItem(
                onItemClick = onMovieItemClick, movie = it, modifier = Modifier
                    .width(197.dp)
                    .height(287.dp)
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun HomeScreenPreview() {
    HomeScreen(AllMovies())
}

@Composable
@Preview
fun MuviesRowPreview() {
    MuviesRow(listOf(movie).plus(movie))
}
