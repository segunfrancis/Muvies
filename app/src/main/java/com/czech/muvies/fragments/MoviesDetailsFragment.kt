package com.czech.muvies.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.czech.muvies.BASE_IMAGE_PATH
import com.czech.muvies.MainActivity
import com.czech.muvies.R
import com.czech.muvies.viewModels.DetailsViewModel
import kotlinx.android.synthetic.main.details_fragment.*
import kotlinx.android.synthetic.main.paged_list.view.*
import java.time.DateTimeException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MoviesDetailsFragment : Fragment() {

    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)

        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inTheatersArgs = MoviesDetailsFragmentArgs.fromBundle(requireArguments()).inTheaterArgs
        val inTheatersSArgs =   MoviesDetailsFragmentArgs.fromBundle(requireArguments()).inTheaterSArgs
        val upcomingArgs = MoviesDetailsFragmentArgs.fromBundle(requireArguments()).upcomingArgs
        val upcomingSArgs = MoviesDetailsFragmentArgs.fromBundle(requireArguments()).upcomingSArgs
        val popularArgs = MoviesDetailsFragmentArgs.fromBundle(requireArguments()).popularArgs
        val popularSArgs = MoviesDetailsFragmentArgs.fromBundle(requireArguments()).popularSArgs
        val topRatedSArgs = MoviesDetailsFragmentArgs.fromBundle(requireArguments()).topRatedSArgs
        val topRatedArgs = MoviesDetailsFragmentArgs.fromBundle(requireArguments()).topRatedArgs

        if (inTheatersArgs != null) {
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${inTheatersArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            Glide.with(this)
                .load("$BASE_IMAGE_PATH${inTheatersArgs.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)

            title.text = inTheatersArgs.title

            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = LocalDate.parse(inTheatersArgs.releaseDate + " 09:10:46", dateFormatter)

            val year = date.year.toString()
            val month = date.month.toString().toLowerCase(Locale.ROOT)

            release_date.text = "$month $year"

            val ratingBar = rating_bar
            val rating = inTheatersArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = inTheatersArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = inTheatersArgs.originalLanguage.toUpperCase(Locale.ROOT)

            overview.text = inTheatersArgs.overview
        }

        if (inTheatersSArgs != null) {
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${inTheatersSArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            Glide.with(this)
                .load("$BASE_IMAGE_PATH${inTheatersSArgs.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)

            title.text = inTheatersSArgs.title

            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = LocalDate.parse(inTheatersSArgs.releaseDate + " 09:10:46", dateFormatter)

            val year = date.year.toString()
            val month = date.month.toString().toLowerCase(Locale.ROOT)

            release_date.text = "$month $year"

            val ratingBar = rating_bar
            val rating = inTheatersSArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = inTheatersSArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = inTheatersSArgs.originalLanguage.toUpperCase(Locale.ROOT)

            overview.text = inTheatersSArgs.overview
        }

        if (upcomingArgs != null) {
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${upcomingArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            Glide.with(this)
                .load("$BASE_IMAGE_PATH${upcomingArgs.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)

            title.text = upcomingArgs.title

            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = LocalDate.parse(upcomingArgs.releaseDate + " 09:10:46", dateFormatter)

            val year = date.year.toString()
            val month = date.month.toString().toLowerCase(Locale.ROOT)

            release_date.text = "$month $year"

            val ratingBar = rating_bar
            val rating = upcomingArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = upcomingArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = upcomingArgs.originalLanguage.toUpperCase(Locale.ROOT)

            overview.text = upcomingArgs.overview
        }

        if (upcomingSArgs != null) {
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${upcomingSArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            Glide.with(this)
                .load("$BASE_IMAGE_PATH${upcomingSArgs.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)

            title.text = upcomingSArgs.title

            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = LocalDate.parse(upcomingSArgs.releaseDate + " 09:10:46", dateFormatter)

            val year = date.year.toString()
            val month = date.month.toString().toLowerCase(Locale.ROOT)

            release_date.text = "$month $year"

            val ratingBar = rating_bar
            val rating = upcomingSArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = upcomingSArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = upcomingSArgs.originalLanguage.toUpperCase(Locale.ROOT)

            overview.text = upcomingSArgs.overview
        }

        if (popularSArgs != null) {
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${popularSArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            Glide.with(this)
                .load("$BASE_IMAGE_PATH${popularSArgs.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)

            title.text = popularSArgs.title

            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = LocalDate.parse(popularSArgs.releaseDate + " 09:10:46", dateFormatter)

            val year = date.year.toString()
            val month = date.month.toString().toLowerCase(Locale.ROOT)

            release_date.text = "$month $year"

            val ratingBar = rating_bar
            val rating = popularSArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = popularSArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = popularSArgs.originalLanguage.toUpperCase(Locale.ROOT)

            overview.text = popularSArgs.overview
        }

        if (popularArgs != null) {
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${popularArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            Glide.with(this)
                .load("$BASE_IMAGE_PATH${popularArgs.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)

            title.text = popularArgs.title

            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = LocalDate.parse(popularArgs.releaseDate + " 09:10:46", dateFormatter)

            val year = date.year.toString()
            val month = date.month.toString().toLowerCase(Locale.ROOT)

            release_date.text = "$month $year"

            val ratingBar = rating_bar
            val rating = popularArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = popularArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = popularArgs.originalLanguage.toUpperCase(Locale.ROOT)

            overview.text = popularArgs.overview
        }

        if (topRatedSArgs != null) {
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${topRatedSArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            Glide.with(this)
                .load("$BASE_IMAGE_PATH${topRatedSArgs.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)

            title.text = topRatedSArgs.title

            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = LocalDate.parse(topRatedSArgs.releaseDate + " 09:10:46", dateFormatter)

            val year = date.year.toString()
            val month = date.month.toString().toLowerCase(Locale.ROOT)

            release_date.text = "$month $year"

            val ratingBar = rating_bar
            val rating = topRatedSArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = topRatedSArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = topRatedSArgs.originalLanguage.toUpperCase(Locale.ROOT)

            overview.text = topRatedSArgs.overview
        }

        if (topRatedArgs != null) {
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${topRatedArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            Glide.with(this)
                .load("$BASE_IMAGE_PATH${topRatedArgs.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)

            title.text = topRatedArgs.title

            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = LocalDate.parse(topRatedArgs.releaseDate + " 09:10:46", dateFormatter)

            val year = date.year.toString()
            val month = date.month.toString().toLowerCase(Locale.ROOT)

            release_date.text = "$month $year"

            val ratingBar = rating_bar
            val rating = topRatedArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = topRatedArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = topRatedArgs.originalLanguage.toUpperCase(Locale.ROOT)

            overview.text = topRatedArgs.overview
        }

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