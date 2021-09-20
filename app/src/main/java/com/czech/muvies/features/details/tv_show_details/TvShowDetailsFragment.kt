package com.czech.muvies.features.details.tv_show_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.czech.muvies.R
import com.czech.muvies.databinding.TvShowDetailsFragmentBinding
import com.czech.muvies.features.details.epoxy.AboutItemAdapter
import com.czech.muvies.features.details.epoxy.CastAdapter
import com.czech.muvies.features.details.epoxy.DetailImageAdapter
import com.czech.muvies.features.details.epoxy.GenreItemAdapter
import com.czech.muvies.features.details.epoxy.SectionHeaderAdapter
import com.czech.muvies.features.details.epoxy.SimilarMoviesAdapter
import com.czech.muvies.features.details.epoxy.SynopsisAdapter
import com.czech.muvies.features.details.model.TvShowsDetailsResponse
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.repository.TvShowsDetailsRepository
import com.czech.muvies.utils.NavigationDeepLinks
import com.czech.muvies.utils.Result
import com.czech.muvies.utils.epoxy.BaseEpoxyController
import com.czech.muvies.utils.epoxy.carouselNoSnap
import com.czech.muvies.utils.epoxy.gridCarouselNoSnap
import com.czech.muvies.utils.launchFragment
import com.czech.muvies.utils.makeGone
import com.czech.muvies.utils.makeVisible
import com.czech.muvies.utils.showErrorMessage
import timber.log.Timber

class TvShowDetailsFragment : Fragment(R.layout.tv_show_details_fragment) {

    private lateinit var binding: TvShowDetailsFragmentBinding
    private val viewModel: TvShowDetailsViewModel by viewModels {
        TvShowDetailsViewModelFactory(
            TvShowsDetailsRepository(MoviesApiService.getService())
        )
    }
    private val args: TvShowDetailsFragmentArgs by navArgs()
    private val controller: TvShowsDetailsController by lazy { TvShowsDetailsController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getTvShowsDetails(args.tvShowId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = TvShowDetailsFragmentBinding.bind(view)
        binding.tvShowsDetailsEpoxyRecyclerView.adapter = controller.adapter
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.tvShowsResponse.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.lottieProgress.makeVisible()
                }
                is Result.Success -> {
                    binding.lottieProgress.makeGone()
                    result.data.let { movieDetails ->
                        // Set toolbar title
                        (requireActivity() as AppCompatActivity).supportActionBar?.title =
                            movieDetails.tvShowDetails?.name
                        controller.data = listOf(movieDetails)
                        controller.requestModelBuild()
                    }
                }
                is Result.Error -> {
                    binding.lottieProgress.makeGone()
                    requireView().showErrorMessage(result.error.localizedMessage)
                    Timber.e(result.error)
                }
            }
        }
    }

    inner class TvShowsDetailsController : BaseEpoxyController<TvShowsDetailsResponse>() {
        override fun buildModels() {
            data?.let { detailsResponse ->
                val details = detailsResponse[0].tvShowDetails
                DetailImageAdapter().apply {
                    movieTitle = details?.name ?: "-"
                    releaseYear = details?.firstAirDate ?: "-"
                    movieLanguage = details?.originalLanguage ?: "-"
                    posterPath = details?.backdropPath ?: "-"
                    fraction = details?.voteAverage ?: 0.0
                    id("image_header")
                    addTo(this@TvShowsDetailsController)
                }

                if (details?.genres.isNullOrEmpty().not()) {
                    SectionHeaderAdapter().apply {
                        sectionTitle = "Genres"
                        id("genre_header")
                        addTo(this@TvShowsDetailsController)
                    }

                    carouselNoSnap {
                        id("genre_carousel")
                        details?.genres?.mapIndexed { index, genre ->
                            GenreItemAdapter().apply {
                                this.genre = genre?.name ?: ""
                                id(index)
                            }
                        }.let { models(it!!) }
                    }
                }

                if (details?.overview != null) {
                    SectionHeaderAdapter().apply {
                        sectionTitle = "Synopsis"
                        id("synopsis_header")
                        addTo(this@TvShowsDetailsController)
                    }

                    SynopsisAdapter().apply {
                        synopsis = details.overview
                        id("synopsis_content")
                        addTo(this@TvShowsDetailsController)
                    }
                }

                if (detailsResponse[0].cast.isNullOrEmpty().not()) {
                    SectionHeaderAdapter().apply {
                        sectionTitle = "Cast"
                        id("cast_header")
                        addTo(this@TvShowsDetailsController)
                    }

                    carouselNoSnap {
                        val casts = detailsResponse[0].cast
                        id("cast_carousel")
                        casts?.mapIndexed { index, cast ->
                            CastAdapter {
                                launchFragment(NavigationDeepLinks.toCastDetails(it))
                            }.apply {
                                imageUrl = cast?.profilePath ?: ""
                                castRealName = cast?.name ?: "-"
                                castPlayName = cast?.character ?: "-"
                                castId = cast?.id ?: 0
                                id(index)
                            }
                        }?.let { models(it) }
                    }
                }

                SectionHeaderAdapter().apply {
                    sectionTitle = "About"
                    id("about_header")
                    addTo(this@TvShowsDetailsController)
                }

                AboutItemAdapter {
                    val browserIntent = Intent(Intent.ACTION_VIEW)
                    browserIntent.data = Uri.parse(it)
                    startActivity(browserIntent)
                }.apply {
                    originalTitle = details?.name ?: "-"
                    runTime = details?.episodeRunTime.toString().plus(" minutes")
                    status = details?.status ?: "-"
                    releaseDate = details?.firstAirDate ?: "-"
                    tagline = "-"
                    voteCount = details?.voteCount.toString().plus(" votes")
                    homePage = details?.homepage ?: "-"
                    id("about_item")
                    addTo(this@TvShowsDetailsController)
                }

                if (details?.seasons.isNullOrEmpty().not()) {
                    SectionHeaderAdapter().apply {
                        sectionTitle = "Seasons"
                        id("seasons_header")
                        addTo(this@TvShowsDetailsController)
                    }

                    carouselNoSnap {
                        id("seasons_carousel")
                        details?.seasons?.mapIndexed { index, season ->
                            SimilarMoviesAdapter { }.apply {
                                imageUrl = season?.posterPath ?: ""
                                id(index)
                            }
                        }.let { models(it!!) }
                    }
                }

                if (detailsResponse[0].similarTvShows.isNullOrEmpty().not()) {
                    SectionHeaderAdapter().apply {
                        sectionTitle = "Similar TV shows"
                        id("similar_movies_header")
                        addTo(this@TvShowsDetailsController)
                    }

                    gridCarouselNoSnap {
                        val similarTvShows = detailsResponse[0].similarTvShows
                        id("similar_carousel")
                        similarTvShows?.mapIndexed { index, movie ->
                            SimilarMoviesAdapter {
                                launchFragment(NavigationDeepLinks.toTvShowDetails(it))
                            }.apply {
                                imageUrl = movie?.posterPath ?: ""
                                movieId = movie?.id ?: 0
                                id(index)
                            }
                        }?.let { models(it) }
                    }
                }
            }
        }
    }
}
