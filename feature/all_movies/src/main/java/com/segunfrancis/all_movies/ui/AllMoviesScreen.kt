package com.segunfrancis.all_movies.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.segunfrancis.muvies.common.Movies
import com.segunfrancis.muvies.common.components.MuviesItemAll
import com.segunfrancis.muvies.common.theme.Grey400

@Composable
fun AllMoviesScreen(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<Movies.MoviesResult>?,
    loading: Boolean,
    onItemClick: (Movies.MoviesResult) -> Unit = {}
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("loader_all_movies_short.json"))
    Surface(modifier = modifier.fillMaxSize()) {
        if (loading) {
            Box(modifier = Modifier.fillMaxSize()) {
                LottieAnimation(
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 24.dp),
                    contentScale = ContentScale.FillWidth
                )
            }
        } else if (movies?.loadState?.append is LoadState.Error) {
            Box(modifier = Modifier.fillMaxSize())
        } else if (movies?.itemSnapshotList?.isNotEmpty() == true) {
            LazyColumn(
                modifier = Modifier,
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                movies.let {
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
    }
}
