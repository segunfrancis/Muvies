package com.czech.muvies.features.details.model

import com.czech.muvies.models.MovieCredits
import com.czech.muvies.models.MovieDetails
import com.czech.muvies.models.SimilarMovies

data class MovieDetailsResponse(
    val details: MovieDetails?,
    val credits: MovieCredits?,
    val similarMovies: SimilarMovies?
)