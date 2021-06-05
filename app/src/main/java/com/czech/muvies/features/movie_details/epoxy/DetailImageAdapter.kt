package com.czech.muvies.features.movie_details.epoxy

import android.view.ViewParent
import android.widget.ImageView
import android.widget.RatingBar
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.czech.muvies.R
import com.czech.muvies.utils.epoxy.KotlinEpoxyHolder
import com.czech.muvies.utils.loadMoviePoster
import com.google.android.material.textview.MaterialTextView

open class DetailImageAdapter : EpoxyModelWithHolder<DetailImageAdapter.DetailImageHolder>() {

    @EpoxyAttribute
    lateinit var posterPath: String

    @EpoxyAttribute
    lateinit var movieTitle: String

    @EpoxyAttribute
    lateinit var releaseYear: String

    @EpoxyAttribute
    lateinit var movieLanguage: String

    @EpoxyAttribute
    var fraction: Double = 0.0

    override fun getDefaultLayout(): Int {
        return R.layout.movie_detail_image_item
    }

    override fun createNewHolder(parent: ViewParent): DetailImageHolder {
        return DetailImageHolder()
    }

    override fun bind(holder: DetailImageHolder) {
        holder.apply {
            poster.loadMoviePoster(posterPath)
            title.text = movieTitle
            year.text = releaseYear
            language.text = movieLanguage
            ratingFraction.text = fraction.toString().plus("/10.0")
            ratingBar.rating = fraction.toFloat().div(2)
        }
    }

    class DetailImageHolder : KotlinEpoxyHolder() {
        val poster by bind<ImageView>(R.id.m_backdrop)
        val title by bind<MaterialTextView>(R.id.m_title)
        val year by bind<MaterialTextView>(R.id.m_release_year)
        val language by bind<MaterialTextView>(R.id.m_lang_text)
        val ratingFraction by bind<MaterialTextView>(R.id.m_rating_fraction)
        val ratingBar by bind<RatingBar>(R.id.md_rating_bar)
    }
}
