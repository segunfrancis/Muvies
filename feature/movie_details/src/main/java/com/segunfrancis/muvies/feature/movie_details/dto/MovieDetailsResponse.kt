package com.segunfrancis.muvies.feature.movie_details.dto

import com.google.gson.annotations.SerializedName

data class MovieDetailsResponse(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection? = null,
    val budget: Long? = null,
    val genres: List<Genre> = emptyList(),
    val homepage: String? = null,
    val id: Int? = null,
    @SerializedName("imdb_id")
    val imdbId: String? = null,
    @SerializedName("original_language")
    val originalLanguage: String? = null,
    @SerializedName("original_title")
    val originalTitle: String? = "",
    val overview: String? = null,
    val popularity: Double? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany?>? = null,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry?>? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    val revenue: Long = 0,
    val runtime: Int? = null,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage?>? = null,
    val status: String? = null,
    val tagline: String? = null,
    val title: String? = "",
    val video: Boolean = false,
    @SerializedName("vote_average")
    val voteAverage: Double = 0.0,
    @SerializedName("vote_count")
    val voteCount: Long = 0,
    @SerializedName("first_air_date")
    val firstAirDate: String? = "",
    @SerializedName("episode_run_time")
    val episodeRunTime: List<Int> = emptyList(),
    val name: String? = "",
    @SerializedName("original_name")
    val originalName: String? = "",
    val seasons: List<Season> = emptyList()
)

data class BelongsToCollection(
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    val id: Int? = null,
    val name: String? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null
)

data class Genre(
    val id: Int,
    val name: String
)

data class ProductionCompany(
    val id: Int? = null,
    @SerializedName("logo_path")
    val logoPath: String? = null,
    val name: String? = null,
    @SerializedName("origin_country")
    val originCountry: String? = null
)

data class ProductionCountry(
    @SerializedName("iso_3166_1")
    val iso31661: String? = null,
    val name: String? = null
)

data class SpokenLanguage(
    @SerializedName("iso_639_1")
    val iso6391: String? = null,
    val name: String? = null
)

data class Season(
    val id: Long = 0,
    val name: String,
    @SerializedName("air_date")
    val airDate: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("episode_count")
    val episodeCount: Int = 0,
    @SerializedName("season_number")
    val seasonNumber: Int = 0,
    @SerializedName("vote_average")
    val voteAverage: Double = 0.0
)
