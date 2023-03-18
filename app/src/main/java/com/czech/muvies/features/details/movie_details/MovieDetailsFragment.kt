package com.czech.muvies.features.details.movie_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.czech.muvies.R
import com.czech.muvies.databinding.MovieDetailsFragmentBinding
import com.czech.muvies.di.InjectorUtils
import com.czech.muvies.features.details.epoxy.AboutItemAdapter
import com.czech.muvies.features.details.epoxy.CastAdapter
import com.czech.muvies.features.details.epoxy.DetailImageAdapter
import com.czech.muvies.features.details.epoxy.GenreItemAdapter
import com.czech.muvies.features.details.epoxy.SectionHeaderAdapter
import com.czech.muvies.features.details.epoxy.SimilarMoviesAdapter
import com.czech.muvies.features.details.epoxy.SynopsisAdapter
import com.czech.muvies.features.details.model.MovieDetailsResponse
import com.czech.muvies.utils.NavigationDeepLinks
import com.czech.muvies.utils.Result
import com.czech.muvies.utils.epoxy.BaseEpoxyController
import com.czech.muvies.utils.epoxy.carouselNoSnap
import com.czech.muvies.utils.epoxy.gridCarouselNoSnap
import com.czech.muvies.utils.launchFragment
import com.czech.muvies.utils.makeGone
import com.czech.muvies.utils.makeVisible
import com.czech.muvies.utils.showErrorMessage
import com.segunfrancis.muvies.common.viewBinding
import kotlinx.parcelize.Parcelize
import timber.log.Timber

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {
    private val binding: MovieDetailsFragmentBinding by viewBinding(MovieDetailsFragmentBinding::bind)
    private val viewModel: MovieDetailsViewModel by viewModels {
        InjectorUtils.ViewModelFactory.provideMovieDetailsViewModelFactory()
    }

    private val args: MovieDetailsFragmentArgs by navArgs()

    private val movieId: Int by lazy { args.movieId }

    private val controller: MovieDetailsController by lazy { MovieDetailsController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel.getMovieDetails(movieId)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.detailsEpoxyRecyclerView.adapter = controller.adapter
        getDetails()
    }

    private fun getDetails() {

        viewModel.movieDetails.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> handleLoading()
                is Result.Success -> handleSuccess(result.data)
                is Result.Error -> handleError(result.error)
            }
        }
    }

    private fun handleLoading() = with(binding) {
        lottieProgress.makeVisible()
    }

    private fun handleError(error: Throwable) = with(binding) {
        lottieProgress.makeGone()
        root.showErrorMessage(error.localizedMessage)
        Timber.e(error)
    }

    private fun handleSuccess(movieDetails: MovieDetailsResponse) = with(binding) {
        lottieProgress.makeGone()
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            movieDetails.details?.title
        controller.data = listOf(movieDetails)
        controller.requestModelBuild()
    }

    inner class MovieDetailsController : BaseEpoxyController<MovieDetailsResponse>() {
        override fun buildModels() {
            data?.let { detailsResponse ->
                val details = detailsResponse[0].details
                DetailImageAdapter().apply {
                    movieTitle = details?.title ?: "-"
                    releaseYear = details?.releaseDate ?: "-"
                    movieLanguage = details?.originalLanguage ?: "-"
                    posterPath = details?.backdropPath ?: "-"
                    fraction = details?.voteAverage ?: 0.0
                    id("image_header")
                    addTo(this@MovieDetailsController)
                }

                SectionHeaderAdapter().apply {
                    sectionTitle = "Genres"
                    id("genre_header")
                    addTo(this@MovieDetailsController)
                }

                carouselNoSnap {
                    id("genre_carousel")
                    details?.genres?.mapIndexed { index, genre ->
                        GenreItemAdapter().apply {
                            this.genre = genre?.name ?: ""
                            id(index)
                        }
                    }?.let { models(it) }
                }

                SectionHeaderAdapter().apply {
                    sectionTitle = "Synopsis"
                    id("synopsis_header")
                    addTo(this@MovieDetailsController)
                }

                SynopsisAdapter().apply {
                    synopsis = details?.overview ?: ""
                    id("synopsis_content")
                    addTo(this@MovieDetailsController)
                }

                SectionHeaderAdapter().apply {
                    sectionTitle = "Cast"
                    id("cast_header")
                    addTo(this@MovieDetailsController)
                }

                carouselNoSnap {
                    val casts = detailsResponse[0].credits
                    id("cast_carousel")
                    casts?.cast?.mapIndexed { index, cast ->
                        CastAdapter {
                            launchFragment(NavigationDeepLinks.toCastDetails(it, cast?.name ?: ""))
                        }.apply {
                            imageUrl = cast?.profilePath ?: ""
                            castRealName = cast?.name ?: "-"
                            castPlayName = cast?.character ?: "-"
                            castId = cast?.id ?: 0
                            id(index)
                        }
                    }?.let { models(it) }
                }

                SectionHeaderAdapter().apply {
                    sectionTitle = "About"
                    id("about_header")
                    addTo(this@MovieDetailsController)
                }

                AboutItemAdapter {
                    val browserIntent = Intent(Intent.ACTION_VIEW)
                    browserIntent.data = Uri.parse(it)
                    startActivity(browserIntent)
                }.apply {
                    originalTitle = details?.originalTitle ?: "-"
                    runTime = details?.runtime.toString().plus(" minutes")
                    status = details?.status ?: "-"
                    releaseDate = details?.releaseDate ?: "-"
                    voteCount = details?.voteCount.toString().plus(" votes")
                    tagline = details?.tagline ?: "-"
                    homePage = details?.homepage ?: "-"
                    id("about_item")
                    addTo(this@MovieDetailsController)
                }

                SectionHeaderAdapter().apply {
                    sectionTitle = "Similar movies"
                    id("similar_movies_header")
                    addTo(this@MovieDetailsController)
                }

                gridCarouselNoSnap {
                    val similarMovies = detailsResponse[0].similarMovies
                    id("similar_carousel")
                    similarMovies?.results?.mapIndexed { index, movie ->
                        SimilarMoviesAdapter {
                            launchFragment(NavigationDeepLinks.toMovieDetails(it, movie?.title ?: ""))
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

@Parcelize
class ToFavorites(
    var movieId: Int?,
    var movieTitle: String?,
    var movieOverview: String?,
    var moviePoster: String?,
    var movieBackdrop: String?,
    var movieDate: String?,
    var movieVote: Double?,
    var movieLang: String?
) : Parcelable
