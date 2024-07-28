package com.segunfrancis.muvies.feature.movie_details.dto

import com.google.gson.annotations.SerializedName

data class MovieCreditsResponse(
    val cast: List<Cast>,
    val crew: List<Crew?>? = null,
    val id: Int? = null
)

data class Crew(
    @SerializedName("credit_id")
    val creditId: String? = null,
    val department: String? = null,
    val gender: Int? = null,
    val id: Int? = null,
    val job: String? = null,
    val name: String? = null,
    @SerializedName("profile_path")
    val profilePath: String? = null
)

data class Cast(
    @SerializedName("cast_id")
    val castId: Int? = null,
    val character: String? = null,
    @SerializedName("credit_id")
    val creditId: String? = null,
    val gender: Int? = null,
    val id: Int? = null,
    val name: String? = null,
    val order: Int? = null,
    @SerializedName("profile_path")
    val profilePath: String? = null
)