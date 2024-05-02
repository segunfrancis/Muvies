package com.segunfrancis.muvies.feature.movie_details.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.segunfrancis.muvies.common.components.MoviePosterSection
import com.segunfrancis.muvies.common.theme.MuviesTheme
import com.segunfrancis.muvies.feature.movie_details.dto.MovieDetailsResponse
import com.segunfrancis.muvies.feature.movie_details.utils.movieDetailsResponse

@Composable
fun MovieDetailsScreen(
    response: MovieDetailsUiResponse
) {
    MovieDetailsContent(response.details)
}

@Composable
fun MovieDetailsContent(details: MovieDetailsResponse?) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            details?.let {
                MoviePosterSection(
                    title = it.title.orEmpty(),
                    rating = it.voteAverage ?: 0.0,
                    language = it.originalLanguage.orEmpty(),
                    posterPath = it.backdropPath.orEmpty(),
                    releaseDate = it.releaseDate.orEmpty(),
                    genres = it.genres?.map { genre -> genre?.name.orEmpty() }.orEmpty(),
                    synopsis = it.overview.orEmpty()
                )
            }
        }
    }
}


@Preview
@Composable
fun MovieDetailsPreview() {
    MuviesTheme {
        MovieDetailsContent(
            details = movieDetailsResponse
        )
    }
}
