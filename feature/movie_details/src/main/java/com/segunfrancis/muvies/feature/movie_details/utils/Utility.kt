package com.segunfrancis.muvies.feature.movie_details.utils

import com.segunfrancis.muvies.feature.movie_details.dto.BelongsToCollection
import com.segunfrancis.muvies.feature.movie_details.dto.Cast
import com.segunfrancis.muvies.feature.movie_details.dto.Genre
import com.segunfrancis.muvies.feature.movie_details.dto.MovieCreditsResponse
import com.segunfrancis.muvies.feature.movie_details.dto.MovieDetailsResponse
import com.segunfrancis.muvies.feature.movie_details.dto.ProductionCompany
import com.segunfrancis.muvies.feature.movie_details.dto.ProductionCountry
import com.segunfrancis.muvies.feature.movie_details.dto.Season
import com.segunfrancis.muvies.feature.movie_details.dto.SimilarMoviesResult

val seasons = listOf(
    Season(airDate = "", name = "", overview = "", posterPath = ""),
    Season(airDate = "", name = "", overview = "", posterPath = ""),
    Season(airDate = "", name = "", overview = "", posterPath = ""),
    Season(airDate = "", name = "", overview = "", posterPath = ""),
    Season(airDate = "", name = "", overview = "", posterPath = ""),
    Season(airDate = "", name = "", overview = "", posterPath = ""),
    Season(airDate = "", name = "", overview = "", posterPath = ""),
    Season(airDate = "", name = "", overview = "", posterPath = "")
)

val movieDetailsResponse = MovieDetailsResponse(
    adult = false,
    backdropPath = "",
    BelongsToCollection(),
    budget = 219035345,
    genres = listOf(
        Genre(id = 1, name = "Adventure"),
        Genre(id = 2, "Action"),
        Genre(3, "Comedy"),
        Genre(4, "Science Fiction")
    ),
    homepage = "https://www.godzillaxkongmovie.com",
    id = 24701,
    imdbId = "tt14539740",
    originalLanguage = "en",
    originalTitle = "Godzilla x Kong: The New Empire",
    overview = "Following their explosive showdown, Godzilla and Kong must reunite against a colossal undiscovered threat hidden within our world, challenging their very existence – and our own.",
    popularity = 1611.66,
    posterPath = "/tMefBSflR6PGQLv7WvFPpKLZkyk.jpg",
    productionCompanies = listOf(ProductionCompany()),
    productionCountries = listOf(ProductionCountry()),
    releaseDate = "2024-03-27",
    revenue = 521400523,
    runtime = 180,
    spokenLanguages = null,
    status = "Released",
    tagline = "Rise together or fall alone.",
    title = "Godzilla x Kong: The New Empire",
    video = true,
    voteAverage = 6.627,
    voteCount = 898,
    seasons = seasons
)

val creditsResponse = MovieCreditsResponse(
    cast = listOf(
        Cast(
            castId = 12,
            character = "Prince Daemon Tarragon",
            name = "Matt Smith",
            profilePath = ""
        ),
        Cast(
            castId = 12,
            character = "Prince Daemon Tarragon",
            name = "Matt Smith",
            profilePath = ""
        ),
        Cast(
            castId = 12,
            character = "Prince Daemon Tarragon",
            name = "Matt Smith",
            profilePath = ""
        ),
        Cast(
            castId = 12,
            character = "Prince Daemon Tarragon",
            name = "Matt Smith",
            profilePath = ""
        ),
        Cast(
            castId = 12,
            character = "Prince Daemon Tarragon",
            name = "Matt Smith",
            profilePath = ""
        ),
    )
)

val similarMoviesResponse = listOf(
    SimilarMoviesResult(id = 1),
    SimilarMoviesResult(id = 2),
    SimilarMoviesResult(id = 3),
    SimilarMoviesResult(id = 4),
    SimilarMoviesResult(id = 5),
    SimilarMoviesResult(id = 6),
    SimilarMoviesResult(id = 7),
    SimilarMoviesResult(id = 8),
    SimilarMoviesResult(id = 9),
    SimilarMoviesResult(id = 10),
    SimilarMoviesResult(id = 11),
    SimilarMoviesResult(id = 12),
    SimilarMoviesResult(id = 13),
    SimilarMoviesResult(id = 14),
    SimilarMoviesResult(id = 15),
    SimilarMoviesResult(id = 16),
)
