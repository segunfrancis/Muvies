package com.czech.muvies.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.czech.muvies.MainActivity
import com.czech.muvies.R
import com.czech.muvies.adapters.MovieCastAdapter
import com.czech.muvies.adapters.MoviesGenreAdapter
import com.czech.muvies.databinding.MovieDetailsFragmentBinding
import com.czech.muvies.models.MovieCredits
import com.czech.muvies.models.MovieDetails
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.pagedAdapters.SimilarMoviesAdapter
import com.czech.muvies.utils.*
import com.czech.muvies.viewModels.MovieDetailsViewModel
import com.czech.muvies.viewModels.MovieDetailsViewModelFactory
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.movie_details_fragment.*
import kotlinx.android.synthetic.main.movie_details_fragment.backdrop
import kotlinx.android.synthetic.main.movie_details_fragment.details
import kotlinx.android.synthetic.main.movie_details_fragment.homepage
import kotlinx.android.synthetic.main.movie_details_fragment.lang_text
import kotlinx.android.synthetic.main.movie_details_fragment.status
import kotlinx.android.synthetic.main.movie_details_fragment.vote_count

@Suppress("UNCHECKED_CAST")
class MovieDetailsFragment : Fragment() {
    private lateinit var binding: MovieDetailsFragmentBinding
    private val viewModel: MovieDetailsViewModel by viewModels { MovieDetailsViewModelFactory(MoviesApiService.getService()) }

    private var genreAdapter = MoviesGenreAdapter()

    private val castAdapter = MovieCastAdapter { requireView().showMessage("Cast ID: $it") }

    private val args: MovieDetailsFragmentArgs by navArgs()

    private val movieId: Int by lazy { args.movieId }

    private var similarMoviesAdapter = SimilarMoviesAdapter {
        launchFragment(MovieDetailsFragmentDirections.actionDetailsFragmentSelf(it))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel.getCasts(movieId)
        viewModel.getMovieDetails(movieId)
        viewModel.getSimilarMovies(movieId)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = MovieDetailsFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.moviesDetailsViewModel = viewModel

        binding.apply {

            movieGenreList.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                adapter = genreAdapter
            }

            similarMovies.apply {
                layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.HORIZONTAL, false)
                adapter = similarMoviesAdapter
            }

            castList.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                adapter = castAdapter
            }
        }

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDetails()
        homepage.movementMethod = LinkMovementMethod.getInstance()
        homepage.setOnClickListener {

            homepage.highlightColor = ContextCompat.getColor(requireContext(), R.color.colorPrimary)

            val url = homepage.text.toString()
            val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.data = Uri.parse(url)
            startActivity(browserIntent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getDetails() {

        viewModel.movieDetails.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {

                }
                is Result.Success -> {
                    result.data.let { movieDetails ->

                        backdrop.loadMoviePoster(movieDetails.backdropPath)

                        genreAdapter.submitList(movieDetails.genres as List<MovieDetails.Genre>)

                        lang_text.text = movieDetails.originalLanguage

                        original_Title.text = movieDetails.originalTitle

                        tag_line.text = movieDetails.tagline

                        homepage.text = movieDetails.homepage

                        status.text = movieDetails.status

                        time.text = Converter.convertTime(movieDetails.runtime!!)

                        release_date.text = Converter.convertDate(movieDetails.releaseDate)

                        vote_count.text = "${movieDetails.voteCount.toString()} votes"

                        overview.text = movieDetails.overview

                        // Set toolbar title
                        (requireActivity() as AppCompatActivity).supportActionBar?.title = movieDetails.title

                        binding.favMovieButton.setOnCheckedChangeListener { _, isChecked ->

                            val intent = Intent()

                            if (isChecked) {

                                Toast.makeText(
                                    requireContext(),
                                    "Checked",
                                    Toast.LENGTH_LONG
                                ).show()

                                val toFavorites = ToFavorites(
                                    movieDetails.id,
                                    movieDetails.title,
                                    movieDetails.overview,
                                    movieDetails.posterPath,
                                    movieDetails.backdropPath,
                                    movieDetails.releaseDate,
                                    movieDetails.voteAverage,
                                    movieDetails.originalLanguage
                                )

                                intent.putExtra(EXTRA_REPLY, toFavorites)


                            } else if (!isChecked) {

                                Toast.makeText(
                                    requireContext(),
                                    "Unchecked",
                                    Toast.LENGTH_LONG
                                ).show()

                            }
                        }
                    }
                    details.visibility = View.VISIBLE
                }
                is Result.Error -> { // TODO: 29/05/2021 Handle error
                }
            }


            viewModel.similarMovies.observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Loading -> {
                    }
                    is Result.Success -> {
                        similarMoviesAdapter.submitList(result.data.results)
                    }
                    is Result.Error -> { // TODO: 29/05/2021 Handle error
                    }
                }

                viewModel.movieCredits.observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Result.Loading -> {
                        }
                        is Result.Success -> {
                            result.data.cast?.let {
                                castAdapter.submitList(result.data.cast as List<MovieCredits.Cast>)
                            }
                        }
                        is Result.Error -> {
                            // TODO: 29/05/2021 handle error
                        }
                    }
                }
            }
        }
    }

    companion object {
        @Deprecated("Use constants from AppConstants class")
        const val EXTRA_REPLY = "com.example.android.favmovieslistsql.REPLY"
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).hideBottomNavigation()
    }

    override fun onDetach() {
        super.onDetach()
        (activity as MainActivity).showBottomNavigation()
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