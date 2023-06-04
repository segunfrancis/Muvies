package com.czech.muvies.di

import com.czech.muvies.features.all.AllMoviesViewModelFactory
import com.czech.muvies.features.cast.CastDetailsViewModelFactory
import com.czech.muvies.features.details.movie_details.MovieDetailsViewModelFactory
import com.czech.muvies.features.details.tv_show_details.TvShowDetailsViewModelFactory
import com.czech.muvies.features.home.MovieViewModelFactory
import com.czech.muvies.features.search.SearchViewModelFactory
import com.czech.muvies.features.tv_shows.TvShowsViewModelFactory
import com.czech.muvies.models.Movies.MoviesResult.MovieCategory
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
        fun provideAllMoviesViewModelFactory(category: MovieCategory): AllMoviesViewModelFactory {
            return AllMoviesViewModelFactory(apiService = getService(), category = category)
        }

        fun provideMovieViewModelFactory(): MovieViewModelFactory {
            return MovieViewModelFactory(repository = Repository.getMovieRepository())
        }

        fun provideTvShowsViewModelFactory(): TvShowsViewModelFactory {
            return TvShowsViewModelFactory(repository = Repository.getTvShowsRepository())
        }

        fun provideMovieDetailsViewModelFactory(): MovieDetailsViewModelFactory {
            return MovieDetailsViewModelFactory(apiService = getService())
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

        fun provideSearchViewModelFactory(): SearchViewModelFactory {
            return SearchViewModelFactory(api = getService())
        }
    }
}
