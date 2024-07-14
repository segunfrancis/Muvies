package com.czech.muvies.di

import androidx.navigation.NavController
import com.czech.muvies.features.cast.CastDetailsViewModelFactory
import com.czech.muvies.features.details.tv_show_details.TvShowDetailsViewModelFactory
import com.czech.muvies.features.home.MovieViewModelFactory
import com.czech.muvies.features.tv_shows.TvShowsViewModelFactory
import com.czech.muvies.navigation.AppNavigation
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.repository.CastRepository
import com.czech.muvies.repository.MovieRepository
import com.czech.muvies.repository.TvShowsDetailsRepository
import com.czech.muvies.repository.TvShowsRepository
import com.czech.muvies.viewModels.SeasonDetailsViewModelFactory

object InjectorUtils {

    private fun getService(): MoviesApiService {
        val apiService: MoviesApiService by lazy {
            MoviesApiService.getService()
        }
        return apiService
    }

    object Repository {
        internal fun getMovieRepository(): MovieRepository {
            return MovieRepository(service = getService())
        }

        internal fun getTvShowsRepository(): TvShowsRepository {
            return TvShowsRepository(moviesApiService = getService())
        }

        internal fun getTvShowsDetailsRepository(): TvShowsDetailsRepository {
            return TvShowsDetailsRepository(moviesApiService = getService())
        }

        internal fun getCastRepository(): CastRepository {
            return CastRepository(service = getService())
        }
    }

    object ViewModelFactory {

        fun provideMovieViewModelFactory(): MovieViewModelFactory {
            return MovieViewModelFactory(repository = Repository.getMovieRepository())
        }

        fun provideTvShowsViewModelFactory(): TvShowsViewModelFactory {
            return TvShowsViewModelFactory(repository = Repository.getTvShowsRepository())
        }

        fun provideTvShowDetailsViewModelFactory(): TvShowDetailsViewModelFactory {
            return TvShowDetailsViewModelFactory(repository = Repository.getTvShowsDetailsRepository())
        }

        fun provideCastDetailsViewModelFactory(castId: Int): CastDetailsViewModelFactory {
            return CastDetailsViewModelFactory(
                castId = castId,
                repository = Repository.getCastRepository()
            )
        }

        fun provideSeasonDetailsViewModelFactory(): SeasonDetailsViewModelFactory {
            return SeasonDetailsViewModelFactory(apiService = getService())
        }
    }

    object Navigator {
        fun getAppNavigator(navController: NavController) = AppNavigation(navController)
    }
}
