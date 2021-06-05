package com.czech.muvies.utils

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.czech.muvies.R
import com.czech.muvies.utils.AppConstants.BASE_IMAGE_PATH
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

fun ImageView.loadMoviePoster(posterPath: String?) {
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

fun Fragment.launchFragment(directions: NavDirections) {
    findNavController().navigate(directions)
}

fun Fragment.launchFragment(destination: Int) {
    findNavController().navigate(destination)
}

fun <T> Flow<T>.doAsync(block: (data: Result<T>) -> Unit): Flow<T> {
    return onStart { block(Result.Loading) }
        .catch { block(Result.Error(it)) }
        .flowOn(Dispatchers.IO)
}

fun View.showMessage(message: String?) {
    message?.let { Snackbar.make(this, it, Snackbar.LENGTH_LONG).show() }
}

fun View.showErrorMessage(message: String?) {
    message?.let {
        val snackbar = Snackbar.make(this, it, Snackbar.LENGTH_LONG)
        snackbar.setBackgroundTint(ContextCompat.getColor(context, R.color.design_default_color_error))
        snackbar.show()
    }
}
