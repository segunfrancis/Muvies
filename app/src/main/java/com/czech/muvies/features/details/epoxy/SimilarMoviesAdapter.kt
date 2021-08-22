package com.czech.muvies.features.details.epoxy

import android.view.ViewParent
import android.widget.ImageView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.czech.muvies.R
import com.czech.muvies.utils.epoxy.KotlinEpoxyHolder
import com.czech.muvies.utils.loadMoviePoster
import com.google.android.material.card.MaterialCardView

open class SimilarMoviesAdapter(private val onClick: (movieId: Int) -> Unit) :
    EpoxyModelWithHolder<SimilarMoviesAdapter.SimilarMoviesHolder>() {

    @EpoxyAttribute
    lateinit var imageUrl: String

    @EpoxyAttribute
    var movieId: Int = 0

    class SimilarMoviesHolder : KotlinEpoxyHolder() {
        val movieImageView by bind<ImageView>(R.id.image_item_img)
        val cardContainer by bind<MaterialCardView>(R.id.image_item_card_view)
    }

    override fun bind(holder: SimilarMoviesHolder) {
        holder.movieImageView.loadMoviePoster(imageUrl)
        holder.cardContainer.setOnClickListener { onClick(movieId) }
    }

    override fun getDefaultLayout(): Int {
        return R.layout.image_item
    }

    override fun createNewHolder(parent: ViewParent): SimilarMoviesHolder {
        return SimilarMoviesHolder()
    }
}
