package com.czech.muvies.features.details.model

import com.czech.muvies.models.SimilarTvShows
import com.czech.muvies.models.TvShowCredits
import com.czech.muvies.models.TvShowDetails

data class TvShowsDetailsResponse(
    val tvShowDetails: TvShowDetails?,
    val similarTvShows: List<SimilarTvShows.SimilarTvShowsResult?>?,
    val cast: List<TvShowCredits.Cast?>?
)
