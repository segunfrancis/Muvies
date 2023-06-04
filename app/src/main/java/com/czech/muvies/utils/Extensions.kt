package com.czech.muvies.utils

import android.view.View
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.czech.muvies.R
import com.czech.muvies.utils.AppConstants.BASE_IMAGE_PATH
import com.czech.muvies.utils.AppConstants.SOURCE_DATE_PATTERN
import com.czech.muvies.utils.AppConstants.TARGET_DATE_PATTERN
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.roundToLong

fun ImageView.loadMoviePoster(posterPath: String?) {
    Glide.with(this)
        .load(BASE_IMAGE_PATH.plus(posterPath))
        .placeholder(R.drawable.poster_placeholder)
        .error(R.drawable.poster_error)
        .into(this)
}

fun ImageView.loadRoundCastImage(path: String?) {
    Glide.with(this)
        .load(BASE_IMAGE_PATH.plus(path))
        .placeholder(R.drawable.person_placeholder)
        .error(R.drawable.person_placeholder)
        .circleCrop()
        .into(this)
}

fun View.makeVisible() {
    if (visibility == View.INVISIBLE || visibility == View.GONE)
        visibility = View.VISIBLE
}

fun View.makeGone() {
    if (visibility == View.VISIBLE)
        visibility = View.GONE
}

fun View.makeInvisible() {
    visibility = View.INVISIBLE
}

fun Fragment.launchFragment(directions: NavDirections) {
    val controller = findNavController()
    if ((controller.currentDestination as FragmentNavigator.Destination).className == javaClass.name) {
        controller.navigate(directions)
    }
}

fun Fragment.launchFragment(@IdRes destination: Int) {
    val controller = findNavController()
    if ((controller.currentDestination as FragmentNavigator.Destination).className == javaClass.name) {
        controller.navigate(destination)
    }
}

fun Fragment.launchFragment(deepLink: String) {
    val controller = findNavController()
    val request = NavDeepLinkRequest.Builder.fromUri(deepLink.toUri())
        .build()
    if ((controller.currentDestination as FragmentNavigator.Destination).className == javaClass.name) {
        controller.navigate(request)
    }
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
        snackbar.setBackgroundTint(
            ContextCompat.getColor(
                context,
                R.color.design_default_color_error
            )
        )
        snackbar.show()
    }
}

fun <T> MutableLiveData<T>.toLiveData(): LiveData<T> {
    return this
}

enum class GENDER { FEMALE, MALE }

fun Int.toGender(): GENDER {
    return when {
        this == 1 -> {
            GENDER.FEMALE
        }
        this == 2 -> {
            GENDER.MALE
        }
        else -> {
            throw IllegalArgumentException("Unknown gender")
        }
    }
}

fun String.convertDate(): String {
    val localDate = LocalDate.parse(this, DateTimeFormatter.ofPattern(SOURCE_DATE_PATTERN))
    return try {
        localDate.format(DateTimeFormatter.ofPattern(TARGET_DATE_PATTERN))
    } catch (e: Exception) {
        Timber.e(e)
        ""
    }
}

fun Double.roundUp(decimalPlaces: Int = 1): Double {
    val multiplier = StringBuilder("1")
    repeat(decimalPlaces) {
        multiplier.append("0")
    }
    val safeMultiplier = try {
        multiplier.toString().toInt()
    } catch (e: Exception) {
        e.printStackTrace()
        1
    }
   return (this * safeMultiplier).roundToLong() / safeMultiplier.toDouble()
}
