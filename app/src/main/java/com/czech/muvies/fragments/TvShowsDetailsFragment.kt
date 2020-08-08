package com.czech.muvies.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.czech.muvies.BASE_IMAGE_PATH
import com.czech.muvies.R
import kotlinx.android.synthetic.main.movies_details_fragment.*
import kotlinx.android.synthetic.main.movies_details_fragment.backdrop
import kotlinx.android.synthetic.main.movies_details_fragment.lang_text
import kotlinx.android.synthetic.main.movies_details_fragment.overview
import kotlinx.android.synthetic.main.movies_details_fragment.poster
import kotlinx.android.synthetic.main.movies_details_fragment.rating_bar
import kotlinx.android.synthetic.main.movies_details_fragment.rating_fraction
import kotlinx.android.synthetic.main.movies_details_fragment.release_date
import kotlinx.android.synthetic.main.tv_shows_details_fragment.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class TvShowsDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tv_shows_details_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val airingTodaySArgs = TvShowsDetailsFragmentArgs.fromBundle(requireArguments()).airingTodaySArgs
        val airingTodayArgs = TvShowsDetailsFragmentArgs.fromBundle(requireArguments()).airingTodayArgs

        if (airingTodaySArgs != null) {
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${airingTodaySArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            Glide.with(this)
                .load("$BASE_IMAGE_PATH${airingTodaySArgs.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)

            name.text = airingTodaySArgs.name

            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = LocalDate.parse(airingTodaySArgs.firstAirDate + " 09:10:46", dateFormatter)

            val year = date.year.toString()
            val month = date.month.toString().toLowerCase(Locale.ROOT)

            release_date.text = "$month $year"

            val ratingBar = rating_bar
            val rating = airingTodaySArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = airingTodaySArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = airingTodaySArgs.originalLanguage.toUpperCase(Locale.ROOT)

            overview.text = airingTodaySArgs.overview
        }

        if (airingTodayArgs != null) {
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${airingTodayArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            Glide.with(this)
                .load("$BASE_IMAGE_PATH${airingTodayArgs.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)

            name.text = airingTodayArgs.name

            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = LocalDate.parse(airingTodayArgs.firstAirDate + " 09:10:46", dateFormatter)

            val year = date.year.toString()
            val month = date.month.toString().toLowerCase(Locale.ROOT)

            release_date.text = "$month $year"

            val ratingBar = rating_bar
            val rating = airingTodayArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = airingTodayArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = airingTodayArgs.originalLanguage.toUpperCase(Locale.ROOT)

            overview.text = airingTodayArgs.overview
        }
    }
}