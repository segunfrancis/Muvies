package com.czech.muvies.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.czech.muvies.BASE_IMAGE_PATH
import com.czech.muvies.MainActivity
import com.czech.muvies.R
import com.czech.muvies.databinding.TvShowDetailsFragmentBinding
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
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${airingTodaySArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

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
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${airingTodayArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

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
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${onAirSArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

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
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${onAirArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

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
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${popularTvSArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

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
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${popularTvArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

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
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${topRatedTvSArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

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
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${topRatedTvArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

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
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${trendingTvSArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

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
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${trendingTvArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            name.text = trendingTvArgs.name

            release_year.text = Converter.convertDateToYear(trendingTvArgs.firstAirDate)

            val ratingBar = rating_bar
            val rating = trendingTvArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = trendingTvArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = trendingTvArgs.originalLanguage

            getDetails(trendingTvArgs.id)
        }
    }

    @SuppressLint("SetTextI18n")
    fun getDetails(showId: Int) {

        viewModel.getTvShowDetails(showId).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data.let { details ->

                            if (details != null) {

                                original_name.text = details.originalName

                                status.text = details.status

                                first_date.text = Converter.convertDate(details.firstAirDate)

                                last_date.text = Converter.convertDate(details.lastAirDate)

                                vote_count.text = "${details.voteCount} votes"

                                seasons.text = "${details.numberOfSeasons} seasons and ${details.numberOfEpisodes} episodes"

                                homepage.text = details.homepage

                                synopsis.text = details.overview
                            }
                        }
                    }
                    Status.LOADING -> {

                    }
                    Status.ERROR -> {

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