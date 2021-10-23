package com.segunfrancis.muvies.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteApiResponse(
    val page: Int,
    val results: List<RemoteMovieResponse>,
    val dates: RemoteDates,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)
