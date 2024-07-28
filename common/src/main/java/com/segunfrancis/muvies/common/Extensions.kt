package com.segunfrancis.muvies.common

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
import com.google.android.material.snackbar.Snackbar
import com.segunfrancis.muvies.common.CommonConstants.BASE_IMAGE_PATH
import com.segunfrancis.muvies.common.CommonConstants.SOURCE_DATE_PATTERN
import com.segunfrancis.muvies.common.CommonConstants.TARGET_DATE_PATTERN
import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException
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

fun Throwable?.handleError(): String {
    return if (this == null) {
        "Something went wrong"
    } else {
        if (this is SocketTimeoutException) {
            "Your network might be slow. Kindly try again"
        } else if (this is UnknownHostException) {
            "We've detected a network problem. Please check your internet connection and try again"
        } else if (this is HttpException && this.code() in 500..599) {
            "We are currently unable to complete your request. Please try again later"
        } else {
            this.localizedMessage ?: "Something went wrong"
        }
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

enum class Type {
    Movie, TvShow
}
