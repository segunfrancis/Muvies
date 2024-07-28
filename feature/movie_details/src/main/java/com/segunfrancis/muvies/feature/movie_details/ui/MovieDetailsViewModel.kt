package com.segunfrancis.muvies.feature.movie_details.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.segunfrancis.muvies.common.Type
import com.segunfrancis.muvies.common.handleError
import com.segunfrancis.muvies.feature.movie_details.api.MovieDetailsService
import com.segunfrancis.muvies.feature.movie_details.dto.MovieCreditsResponse
import com.segunfrancis.muvies.feature.movie_details.dto.MovieDetailsResponse
import com.segunfrancis.muvies.feature.movie_details.dto.SimilarMoviesResult
import com.segunfrancis.muvies.feature.movie_details.repo.MovieDetailsRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val detailsRepository: MovieDetailsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailsUiResponse())
    val uiState = _uiState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _uiState.update {
            it.copy(error = throwable.handleError(), loading = false)
        }
    }

    private val type = savedStateHandle.get<Type>("type") ?: Type.Movie

    init {
        savedStateHandle.get<Long>("movieOrSeriesId")?.let {
            getMovieDetails(it, type)
        }
    }

    private fun getMovieDetails(movieOrSeriesId: Long, type: Type) {
        _uiState.update { it.copy(loading = true) }
        viewModelScope.launch(exceptionHandler) {
            val response = detailsRepository.getDetails(id = movieOrSeriesId, type = type)
            _uiState.update {
                it.copy(
                    details = response.details?.getOrNull(),
                    similarMovies = response.similarMovies?.getOrNull(),
                    credits = response.credits?.getOrNull(),
                    loading = false
                )
            }
            _uiState.update {
                it.copy(
                    error = if (response.details?.isFailure == true) response.details.exceptionOrNull()
                        .handleError() else if (response.similarMovies?.isFailure == true) response.similarMovies.exceptionOrNull()
                        .handleError() else if (response.credits?.isFailure == true) response.credits.exceptionOrNull()
                        .handleError() else null
                )
            }
        }
    }
}

data class MovieDetailsUiResponse(
    val details: MovieDetailsResponse? = null,
    val similarMovies: List<SimilarMoviesResult>? = null,
    val credits: MovieCreditsResponse? = null,
    val error: String? = null,
    val loading: Boolean = false
)

@Suppress("UNCHECKED_CAST")
fun movieDetailsViewModelFactory(apiService: MovieDetailsService) =
    object : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
                return MovieDetailsViewModel(
                    detailsRepository = MovieDetailsRepository(apiService),
                    savedStateHandle = extras.createSavedStateHandle()
                ) as T
            }
            throw IllegalArgumentException("Unknown class name")
        }
    }
