package com.segunfrancis.muvies.feature.movie_details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.segunfrancis.muvies.common.CommonConstants
import com.segunfrancis.muvies.feature.movie_details.R
import com.segunfrancis.muvies.feature.movie_details.dto.SimilarMoviesResult
import com.segunfrancis.muvies.feature.movie_details.utils.similarMoviesResponse

@Composable
fun SimilarMoviesComponent(
    headerTitle: String,
    similarMovies: List<SimilarMoviesResult>,
    onSimilarMovieClick: (id: Long, title: String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = headerTitle,
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Normal)
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyHorizontalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .height(470.dp),
            rows = GridCells.Fixed(count = 2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(similarMovies) {
                SimilarMoviesItem(
                    imageUrl = it.posterPath.orEmpty(),
                    movieTitle = it.title.orEmpty(),
                    onClick = { onSimilarMovieClick(it.id, it.title.orEmpty()) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SimilarMoviesItem(
    imageUrl: String,
    movieTitle: String,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    Card(
        onClick = { onClick() },
        modifier = Modifier
            .width(150.dp)
            .height(230.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 12.dp,
        onClickLabel = movieTitle
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data("${CommonConstants.BASE_IMAGE_PATH}${imageUrl}")
                .placeholder(R.drawable.poster_placeholder).error(R.drawable.poster_error)
                .build(),
            contentDescription = movieTitle,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
fun SimilarMoviesComponentPreview() {
    SimilarMoviesComponent(headerTitle = "SIMILAR MOVIES", similarMovies = similarMoviesResponse) { _, _ -> }
}
