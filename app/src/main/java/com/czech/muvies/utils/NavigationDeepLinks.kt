package com.czech.muvies.utils

object NavigationDeepLinks {
    fun toMovieDetails(movieId: Int): String = "https://czech.muvies/movie_details/$movieId"
    fun toTvShowDetails(tvShowId: Int): String = "https://czech.muvies/tv_show_details/$tvShowId"
    fun toCastDetails(castId: Int): String = "https://czech.muvies/cast_details/$castId"
    fun toAllMovies(): String = "https://czech.muvies/all_movies"
}
