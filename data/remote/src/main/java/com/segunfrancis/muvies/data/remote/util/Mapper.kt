package com.segunfrancis.muvies.data.remote.util

import com.segunfrancis.muvies.data.remote.model.RemoteApiResponse
import com.segunfrancis.muvies.data.remote.model.RemoteDates
import com.segunfrancis.muvies.data.remote.model.RemoteMovieResponse
import com.segunfrancis.muvies.domain.model.DomainApiResponse
import com.segunfrancis.muvies.domain.model.DomainDates
import com.segunfrancis.muvies.domain.model.DomainMovieResponse

private fun RemoteDates.mapDatesToDomain(): DomainDates {
    return DomainDates(maximum, minimum)
}

private fun RemoteMovieResponse.mapMoviesToDomain(): DomainMovieResponse {
    return DomainMovieResponse(
        adult,
        backdropPath,
        genreIds,
        id,
        originalLanguage,
        originalTitle,
        overview,
        popularity,
        posterPath,
        releaseDate,
        title,
        video,
        voteAverage,
        voteCount
    )
}

fun RemoteApiResponse.mapApiToDomain(): DomainApiResponse {
    return DomainApiResponse(
        page = page,
        dates = dates.mapDatesToDomain(),
        results = results.map { result -> result.mapMoviesToDomain() },
        totalPages = totalPages,
        totalResults = totalResults
    )
}
