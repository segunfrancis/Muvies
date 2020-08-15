package com.czech.muvies.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.czech.muvies.BASE_IMAGE_PATH
import com.czech.muvies.MainActivity
import com.czech.muvies.R
import com.czech.muvies.adapters.SeasonsAdapter
import com.czech.muvies.adapters.ShowsGenreAdapter
import com.czech.muvies.adapters.seasonsItemClickListener
import com.czech.muvies.databinding.TvShowDetailsFragmentBinding
import com.czech.muvies.models.TvShowDetails
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.utils.Converter
import com.czech.muvies.utils.Status
import com.czech.muvies.viewModels.TvShowDetailsViewModel
import com.czech.muvies.viewModels.TvShowDetailsViewModelFactory
import kotlinx.android.synthetic.main.movie_details_fragment.backdrop
import kotlinx.android.synthetic.main.movie_details_fragment.lang_text
import kotlinx.android.synthetic.main.movie_details_fragment.overview
import kotlinx.android.synthetic.main.movie_details_fragment.rating_bar
import kotlinx.android.synthetic.main.movie_details_fragment.rating_fraction
import kotlinx.android.synthetic.main.movie_details_fragment.release_year
import kotlinx.android.synthetic.main.tv_show_details_fragment.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class TvShowDetailsFragment : Fragment() {

    private lateinit var viewModel: TvShowDetailsViewModel
    private lateinit var binding: TvShowDetailsFragmentBinding

    private var genreAdapter = ShowsGenreAdapter(arrayListOf())

    private val seasonsClickListener by lazy {
        object : seasonsItemClickListener {
            override fun invoke(show: TvShowDetails, season: TvShowDetails.Season) {
                val args = TvShowDetailsFragmentDirections.actionTvShowsDetailsFragmentToSeasonDetailsFragment(season, show)
                findNavController().navigate(args)
            }
        }
    }
    private var seasonsAdapter = SeasonsAdapter(arrayListOf(), seasonsClickListener)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = TvShowDetailsFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this, TvShowDetailsViewModelFactory(MoviesApiService.getService()))
            .get(TvShowDetailsViewModel::class.java)
        binding.lifecycleOwner = this
        binding.tvShowsDetailsViewModel = viewModel

        // Inflate the layout for this fragment
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val airingTodaySArgs = TvShowDetailsFragmentArgs.fromBundle(requireArguments()).airingTodaySArgs
        val airingTodayArgs = TvShowDetailsFragmentArgs.fromBundle(requireArguments()).airingTodayArgs
        val onAirSArgs = TvShowDetailsFragmentArgs.fromBundle(requireArguments()).onAirSArgs
        val onAirArgs = TvShowDetailsFragmentArgs.fromBundle(requireArguments()).onAirArgs
        val popularTvSArgs = TvShowDetailsFragmentArgs.fromBundle(requireArguments()).popularTvSArgs
        val popularTvArgs = TvShowDetailsFragmentArgs.fromBundle(requireArguments()).popularTvArgs
        val topRatedTvSArgs = TvShowDetailsFragmentArgs.fromBundle(requireArguments()).topRatedTvSArgs
        val topRatedTvArgs = TvShowDetailsFragmentArgs.fromBundle(requireArguments()).topRatedTvArgs
        val trendingTvSArgs = TvShowDetailsFragmentArgs.fromBundle(requireArguments()).trendingTvSArgs
        val trendingTvArgs = TvShowDetailsFragmentArgs.fromBundle(requireArguments()).trendingTvArgs

        if (airingTodaySArgs != null) {

            name.text = airingTodaySArgs.name

            release_year.text = Converter.convertDateToYear(airingTodaySArgs.firstAirDate)

            val ratingBar = rating_bar
            val rating = airingTodaySArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = airingTodaySArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = airingTodaySArgs.originalLanguage

            getDetails(airingTodaySArgs.id)
        }

        if (airingTodayArgs != null) {

            name.text = airingTodayArgs.name

            release_year.text = Converter.convertDateToYear(airingTodayArgs.firstAirDate)

            val ratingBar = rating_bar
            val rating = airingTodayArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = airingTodayArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = airingTodayArgs.originalLanguage

            getDetails(airingTodayArgs.id)
        }

        if (onAirSArgs != null) {

            name.text = onAirSArgs.name

            release_year.text = Converter.convertDateToYear(onAirSArgs.firstAirDate)

            val ratingBar = rating_bar
            val rating = onAirSArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = onAirSArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = onAirSArgs.originalLanguage

            getDetails(onAirSArgs.id)
        }

        if (onAirArgs != null) {

            name.text = onAirArgs.name

            release_year.text = Converter.convertDateToYear(onAirArgs.firstAirDate)

            val ratingBar = rating_bar
            val rating = onAirArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = onAirArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = onAirArgs.originalLanguage

            getDetails(onAirArgs.id)
        }

        if (popularTvSArgs != null) {

            name.text = popularTvSArgs.name

            release_year.text = Converter.convertDateToYear(popularTvSArgs.firstAirDate)

            val ratingBar = rating_bar
            val rating = popularTvSArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = popularTvSArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = popularTvSArgs.originalLanguage

            getDetails(popularTvSArgs.id)
        }

        if (popularTvArgs != null) {

            name.text = popularTvArgs.name

            release_year.text = Converter.convertDateToYear(popularTvArgs.firstAirDate)

            val ratingBar = rating_bar
            val rating = popularTvArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = popularTvArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = popularTvArgs.originalLanguage

            getDetails(popularTvArgs.id)
        }

        if (topRatedTvSArgs != null) {

            name.text = topRatedTvSArgs.name

            release_year.text = Converter.convertDateToYear(topRatedTvSArgs.firstAirDate)

            val ratingBar = rating_bar
            val rating = topRatedTvSArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = topRatedTvSArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = topRatedTvSArgs.originalLanguage

            getDetails(topRatedTvSArgs.id)
        }

        if (topRatedTvArgs != null) {

            name.text = topRatedTvArgs.name

            release_year.text = Converter.convertDateToYear(topRatedTvArgs.firstAirDate)

            val ratingBar = rating_bar
            val rating = topRatedTvArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = topRatedTvArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = topRatedTvArgs.originalLanguage

            getDetails(topRatedTvArgs.id)
        }

        if (trendingTvSArgs != null) {

            name.text = trendingTvSArgs.name

            release_year.text = Converter.convertDateToYear(trendingTvSArgs.firstAirDate)

            val ratingBar = rating_bar
            val rating = trendingTvSArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = trendingTvSArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = trendingTvSArgs.originalLanguage

            getDetails(trendingTvSArgs.id)
        }

        if (trendingTvArgs != null) {

            name.text = trendingTvArgs.name

            release_year.text = Converter.convertDateToYear(trendingTvArgs.firstAirDate)

            val ratingBar = rating_bar
            val rating = trendingTvArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = trendingTvArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = trendingTvArgs.originalLanguage

            getDetails(trendingTvArgs.id)
        }

        binding.showsGenreList.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = genreAdapter
        }

        binding.seasonsList.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = seasonsAdapter
        }
    }

    @SuppressLint("SetTextI18n")
    fun getDetails(showId: Int) {

        viewModel.getTvShowDetails(showId).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data.let { showDetails ->

                            if (showDetails != null) {

                                Glide.with(this)
                                    .load("$BASE_IMAGE_PATH${showDetails.backdropPath}")
                                    .placeholder(R.drawable.backdrop_placeholder)
                                    .into(backdrop)

                                genreAdapter.updateList(showDetails.genres as List<TvShowDetails.Genre>)

                                seasonsAdapter.updateList(showDetails.seasons as List<TvShowDetails.Season>)

                                original_name.text = showDetails.originalName

                                status.text = showDetails.status

                                first_date.text = Converter.convertDate(showDetails.firstAirDate)

                                last_date.text = Converter.convertDate(showDetails.lastAirDate)

                                next_date.text = Converter.convertDate(showDetails.nextEpisodeToAir?.airDate)

                                next_episode.text =
                                    "Season ${showDetails.nextEpisodeToAir?.seasonNumber} episode ${showDetails.nextEpisodeToAir?.episodeNumber}"

                                vote_count.text = "${showDetails.voteCount} votes"

                                seasons.text = "${showDetails.numberOfSeasons} seasons ${showDetails.numberOfEpisodes} episodes"

                                homepage.text = showDetails.homepage

                                synopsis.text = showDetails.overview
                            }
                        }
                        details.visibility = View.VISIBLE
                    }
                    Status.LOADING -> {
                        details.visibility = View.INVISIBLE
                    }
                    Status.ERROR -> {
                        Toast.makeText(requireContext(), "error", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
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