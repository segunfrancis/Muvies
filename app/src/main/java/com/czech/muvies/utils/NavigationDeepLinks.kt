package com.czech.muvies.utils

object NavigationDeepLinks {
    fun toMovieDetails(movieId: Int, movieTitle: String): String = "https://czech.muvies/movie_details/$movieId/$movieTitle"
    fun toTvShowDetails(tvShowId: Int, tvShowTitle: String): String = "https://czech.muvies/tv_show_details/$tvShowId/$tvShowTitle"
    fun toCastDetails(castId: Int, castName: String): String = "https://czech.muvies/cast_details/$castId/$castName"
    fun toAllMovies(category: String, movieTitle: String): String = "https://czech.muvies/all_movies/$category/$movieTitle"
}
