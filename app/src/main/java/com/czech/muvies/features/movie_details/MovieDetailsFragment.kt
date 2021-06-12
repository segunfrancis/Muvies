package com.czech.muvies.features.movie_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.czech.muvies.databinding.MovieDetailsFragmentBinding
import com.czech.muvies.features.movie_details.epoxy.*
import com.czech.muvies.features.movie_details.model.MovieDetailsResponse
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.utils.*
import com.czech.muvies.utils.epoxy.BaseEpoxyController
import com.czech.muvies.utils.epoxy.carouselNoSnap
import com.czech.muvies.utils.epoxy.gridCarouselNoSnap
import kotlinx.android.parcel.Parcelize
import timber.log.Timber

@Suppress("UNCHECKED_CAST")
class MovieDetailsFragment : Fragment() {
    private lateinit var binding: MovieDetailsFragmentBinding
    private val viewModel: MovieDetailsViewModel by viewModels { MovieDetailsViewModelFactory(MoviesApiService.getService()) }

    private val args: MovieDetailsFragmentArgs by navArgs()

    private val movieId: Int by lazy { args.movieId }

    private val controller: MovieDetailsController by lazy { MovieDetailsController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel.getMovieDetails(movieId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MovieDetailsFragmentBinding.inflate(inflater)
        binding.detailsEpoxyRecyclerView.adapter = controller.adapter
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDetails()
    }

    private fun getDetails() {

        viewModel.movieDetails.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.lottieProgress.makeVisible()
                }
                is Result.Success -> {
                    binding.lottieProgress.makeGone()
                    result.data.let { movieDetails ->
                        // Set toolbar title
                        (requireActivity() as AppCompatActivity).supportActionBar?.title = movieDetails.details?.title
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

    companion object {
        @Deprecated("Use constants from AppConstants class")
        const val EXTRA_REPLY = "com.example.android.favmovieslistsql.REPLY"
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
                            launchFragment(MovieDetailsFragmentDirections.actionDetailsFragmentToCastDetailsFragment(it))
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
                            launchFragment(
                                MovieDetailsFragmentDirections.actionDetailsFragmentSelf(
                                    it
                                )
                            )
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
