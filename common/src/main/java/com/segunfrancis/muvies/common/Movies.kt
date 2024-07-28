package com.segunfrancis.muvies.common

import com.google.gson.annotations.SerializedName

data class Movies(
    val results: List<MoviesResult> = listOf(),
    val page: Int = 0,
    @SerializedName("total_results")
    val totalResults: Int = 0,
    val dates: InTheatersDates = InTheatersDates(),
    @SerializedName("total_pages")
    val totalPages: Int = 0
) {

    data class MoviesResult(
        val popularity: Double = 0.0,
        @SerializedName("vote_count")
        val voteCount: Int = 0,
        val video: Boolean = false,
        @SerializedName("poster_path")
        val posterPath: String? = "",
        val id: Long = 0,
        val adult: Boolean = false,
        @SerializedName("backdrop_path")
        val backdropPath: String? = "",
        @SerializedName("original_language")
        val originalLanguage: String = "",
        @SerializedName("original_title")
        val originalTitle: String = "",
        @SerializedName("genre_ids")
        val genreIds: List<Int> = listOf(),
        val title: String = "",
        @SerializedName("vote_average")
        val voteAverage: Double = 0.0,
        val overview: String = "",
        @SerializedName("release_date")
        val releaseDate: String = "",
        @SerializedName("original_name")
        val originalName: String = "",
        val name: String = "",
        @SerializedName("first_air_date")
        val firstAirDate: String = "",
        var movieCategory: MovieCategory? = null
    ) {
        enum class MovieCategory(val formattedName: String, val value: String) {
            IN_THEATER("in theater", "in_theater"),
            UPCOMING("upcoming", "upcoming"),
            POPULAR("popular", "popular"),
            TOP_RATED("top rated", "top_rated"),
            TRENDING("trending", "trending")
        }
    }

    data class InTheatersDates(
        val maximum: String = "",
        val minimum: String = ""
    )
}

val movie = Movies.MoviesResult(
    popularity = 5.4,
    voteCount = 296,
    video = true,
    posterPath = "",
    id = 1,
    adult = false,
    backdropPath = "",
    originalLanguage = "English",
    originalTitle = "Clash of the Titans",
    genreIds = listOf(),
    title = "Clash of the Titans",
    voteAverage = 6.7,
    overview = "",
    releaseDate = "12-8-2021",
    movieCategory = null
)
