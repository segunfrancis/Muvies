package com.czech.muvies.features.tv_shows

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.czech.muvies.R
import com.czech.muvies.databinding.TvShowsFragmentBinding
import com.czech.muvies.features.home.epoxy.MainMovieHolder
import com.czech.muvies.features.home.epoxy.MovieHeaderHolder
import com.czech.muvies.features.home.epoxy.SubMovieHolder
import com.czech.muvies.models.TvShows
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.repository.TvShowsRepository
import com.czech.muvies.utils.NavigationDeepLinks
import com.czech.muvies.utils.Result
import com.czech.muvies.utils.epoxy.BaseEpoxyController
import com.czech.muvies.utils.epoxy.carouselNoSnap
import com.czech.muvies.utils.launchFragment
import com.czech.muvies.utils.makeGone
import com.czech.muvies.utils.makeVisible
import com.czech.muvies.utils.showErrorMessage
import com.czech.muvies.utils.showMessage
import timber.log.Timber

class TvShowsFragment : Fragment(R.layout.tv_shows_fragment) {

    private val controller: TvShowsAdapterController by lazy { TvShowsAdapterController() }
    private val tvShowsViewModel: TvShowsViewModel by viewModels {
        TvShowsViewModelFactory(
            TvShowsRepository(
                MoviesApiService.getService()
            )
        )
    }
    private lateinit var binding: TvShowsFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = TvShowsFragmentBinding.bind(view)
        binding.tvShowsEpoxyRecyclerView.adapter = controller.adapter
        setupObservers()
    }

    private fun setupObservers() {
        tvShowsViewModel.tvShowsResponse.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.lottieProgress.makeVisible()
                }
                is Result.Success -> {
                    binding.lottieProgress.makeGone()
                    controller.data = result.data
                    controller.requestModelBuild()
                }
                is Result.Error -> {
                    requireView().showErrorMessage(result.error.localizedMessage)
                    binding.lottieProgress.makeGone()
                    Timber.e(result.error)
                }
            }
        }
    }

    inner class TvShowsAdapterController : BaseEpoxyController<TvShows.TvShowsResult?>() {

        override fun buildModels() {

            data?.let { tvShows ->

                val tvShowMap = tvShows.groupBy {
                    it?.category
                }
                tvShowMap.keys.forEachIndexed { index, tvShowCategory ->
                    carouselNoSnap {
                        MovieHeaderHolder { title ->
                            requireView().showMessage(title)
                        }.apply {
                            title = tvShowCategory!!.value
                            id(tvShowCategory.name.plus(index))
                            addTo(this@TvShowsAdapterController)
                        }

                        id(tvShowCategory?.name.plus(" carousel"))
                        models(tvShows.filter { tvShow ->
                            tvShow?.category == tvShowCategory
                        }.mapIndexed { index, tvShowsResult ->
                            if (tvShowsResult?.category == TvShows.TvShowsCategory.AIRING_TODAY) {
                                MainMovieHolder {
                                    launchFragment(NavigationDeepLinks.toTvShowDetails(it))
                                }.apply {
                                    tvShowsResult.let {
                                        title = it.name
                                        imageUrl = it.posterPath
                                        movieId = it.id
                                    }
                                    id(index)
                                }
                            } else {
                                SubMovieHolder {
                                    launchFragment(NavigationDeepLinks.toTvShowDetails(it))
                                }.apply {
                                    tvShowsResult?.let {
                                        title = it.name
                                        imageUrl = it.posterPath
                                        movieId = it.id
                                    }
                                    id(index)
                                }
                            }
                        })
                    }
                }
            }
        }
    }
}
