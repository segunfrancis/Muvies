package com.czech.muvies.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.czech.muvies.BASE_IMAGE_PATH
import com.czech.muvies.R

fun ImageView.loadMoviePoster(posterPath: String) {
    Glide.with(this)
        .load(BASE_IMAGE_PATH.plus(posterPath))
        .placeholder(R.drawable.poster_placeholder)
        .error(R.drawable.poster_error)
        .into(this)
}

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}

fun View.makeInvisible() {
    visibility = View.INVISIBLE
}