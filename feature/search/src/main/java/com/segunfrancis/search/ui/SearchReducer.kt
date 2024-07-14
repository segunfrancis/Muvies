package com.segunfrancis.search.ui

import com.segunfrancis.muvies.common.Reducer


class SearchReducer : Reducer<SearchScreenActions, SearchMoviesUiState> {
    override fun invoke(
        action: SearchScreenActions,
        currentState: SearchMoviesUiState
    ): SearchMoviesUiState {
        return when (action) {
            is SearchScreenActions.ShowError -> {
                currentState.copy(
                    movies = emptyList(),
                    isLoading = false,
                    error = action.error.localizedMessage
                )
            }

            is SearchScreenActions.ShowMovies -> {
                currentState.copy(movies = action.movies, isLoading = false, error = null)
            }

            SearchScreenActions.ShowNoMovies -> {
                currentState.copy(movies = emptyList(), isLoading = false, error = null)
            }

            SearchScreenActions.ShowLoader -> {
                currentState.copy(isLoading = true, error = null)
            }

            is SearchScreenActions.UpdateSearchText -> {
                currentState.copy(searchText = action.searchText)
            }

            is SearchScreenActions.UpdateSearchType -> {
                currentState.copy(type = action.searchType)
            }
        }
    }
}
