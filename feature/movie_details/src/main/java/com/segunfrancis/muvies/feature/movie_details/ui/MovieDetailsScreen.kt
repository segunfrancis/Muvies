package com.segunfrancis.muvies.feature.movie_details.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.segunfrancis.muvies.feature.movie_details.components.MoviePosterSection
import com.segunfrancis.muvies.common.theme.MuviesTheme
import com.segunfrancis.muvies.feature.movie_details.components.AboutComponent
import com.segunfrancis.muvies.feature.movie_details.components.CastsComponent
import com.segunfrancis.muvies.feature.movie_details.components.SimilarMoviesComponent
import com.segunfrancis.muvies.feature.movie_details.utils.creditsResponse
import com.segunfrancis.muvies.feature.movie_details.utils.movieDetailsResponse
import com.segunfrancis.muvies.feature.movie_details.utils.similarMoviesResponse

@Composable
fun MovieDetailsScreen(
    response: MovieDetailsUiResponse, onIntent: (MovieDetailsIntents) -> Unit
) {
    MovieDetailsContent(response, onIntent = { onIntent(it) })
}

@Composable
fun MovieDetailsContent(response: MovieDetailsUiResponse, onIntent: (MovieDetailsIntents) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState(), enabled = true)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            response.details?.let {
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
            Spacer(Modifier.height(24.dp))
            response.credits?.cast?.let { casts ->
                CastsComponent(casts)
            }
            Spacer(Modifier.height(24.dp))
            response.details?.let {
                AboutComponent(
                    originalTitle = it.originalTitle,
                    runtime = String.format("${it.runtime} %s", "minutes"),
                    status = it.status,
                    voteCount = String.format("${it.voteCount} %s", "votes"),
                    releaseDate = it.releaseDate,
                    tagLine = it.tagline,
                    homePage = it.homepage
                )
            }
            Spacer(Modifier.height(24.dp))
            response.similarMovies?.let { movies ->
                if (movies.isNotEmpty()) {
                    SimilarMoviesComponent(
                        movies,
                        onSimilarMovieClick = { id, title ->
                            onIntent(
                                MovieDetailsIntents.ViewMovieDetails(
                                    id,
                                    title
                                )
                            )
                        })
                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}

sealed interface MovieDetailsIntents {
    data class ViewMovieDetails(val id: Int, val movieTitle: String) : MovieDetailsIntents
    data class ViewCastDetails(val id: Int) : MovieDetailsIntents
}

@Preview
@Composable
fun MovieDetailsPreview() {
    MuviesTheme {
        MovieDetailsContent(
            response = detailsResponse,
            onIntent = {}
        )
    }
}

val detailsResponse = MovieDetailsUiResponse(
    details = movieDetailsResponse,
    credits = creditsResponse,
    similarMovies = similarMoviesResponse
)
