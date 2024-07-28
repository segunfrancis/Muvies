package com.segunfrancis.search.navigation

import com.segunfrancis.muvies.common.Movies
import com.segunfrancis.muvies.common.Type

interface SearchNavigation {
    fun navigateToDetails(result: Movies.MoviesResult, type: Type)
}
