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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.czech.muvies.components.CategoryHeader
import com.czech.muvies.components.MuviesItemHome
import com.czech.muvies.components.movie
import com.segunfrancis.muvies.common.Movies
import com.segunfrancis.muvies.common.Movies.MoviesResult.MovieCategory

@Composable
fun HomeScreen(
    movies: AllMovies,
    modifier: Modifier = Modifier,
    onMovieItemClick: (Movies.MoviesResult) -> Unit = {},
    onSeeAllClick: (MovieCategory) -> Unit = {}
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
                    if (key == MovieCategory.IN_THEATER) {
                        MuviesRow(
                            muvies = value,
                            cardWidth = 195.dp,
                            cardHeight = 287.dp,
                            onMovieItemClick = { onMovieItemClick.invoke(it) })
                    } else {
                        MuviesRow(
                            muvies = value,
                            cardWidth = 167.dp,
                            cardHeight = 267.dp,
                            onMovieItemClick = { onMovieItemClick.invoke(it) })
                    }
                }
            }
        }
    }
}

@Composable
fun MuviesRow(
    muvies: List<Movies.MoviesResult>,
    cardWidth: Dp,
    cardHeight: Dp,
    onMovieItemClick: (Movies.MoviesResult) -> Unit = {}
) {
    LazyRow(
        contentPadding = PaddingValues(all = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items = muvies) {
            MuviesItemHome(
                onItemClick = onMovieItemClick, movie = it, modifier = Modifier
                    .width(cardWidth)
                    .height(cardHeight)
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
    MuviesRow(cardHeight = 170.dp, cardWidth = 140.dp, muvies = listOf(movie).plus(movie))
}
