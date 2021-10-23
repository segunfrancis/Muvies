package com.segunfrancis.muvies.domain.model

data class DomainApiResponse(
    val page: Int,
    val results: List<DomainMovieResponse>,
    val dates: DomainDates,
    val totalPages: Int,
    val totalResults: Int
)
