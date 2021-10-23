package com.segunfrancis.muvies.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteMovieResponse(
    val adult: Boolean,
    @SerializedName(value = "backdrop_path") val backdropPath: String,
    @SerializedName(value = "genre_ids") val genreIds: List<Int>,
    val id: Int,
    @SerializedName(value = "original_language") val originalLanguage: String,
    @SerializedName(value = "original_title") val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName(value = "poster_path") val posterPath: String,
    @SerializedName(value = "release_date") val releaseDate: String,
    val title: String,
    val video: Boolean,
    @SerializedName(value = "vote_average") val voteAverage: Double,
    @SerializedName(value = "vote_count") val voteCount: Int
)
