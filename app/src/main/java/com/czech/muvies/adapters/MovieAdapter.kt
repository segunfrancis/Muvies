package com.czech.muvies.adapters

import android.view.ViewParent
import android.widget.ImageView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.czech.muvies.R
import com.czech.muvies.utils.epoxy.KotlinEpoxyHolder
import com.czech.muvies.utils.loadMoviePoster
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView

open class MainMovieHolder(private val onClick: (movieId: Int) -> Unit) : EpoxyModelWithHolder<MovieHolder>() {

    @EpoxyAttribute
    lateinit var title: String

    @EpoxyAttribute
    lateinit var imageUrl: String

    @EpoxyAttribute
    var movieId: Int = 0

    override fun getDefaultLayout(): Int {
        return R.layout.main_movie_item
    }

    override fun createNewHolder(parent: ViewParent): MovieHolder {
        return MovieHolder()
    }

    override fun bind(holder: MovieHolder) {
        holder.title.text = title
        holder.image.loadMoviePoster(imageUrl)
        holder.card.setOnClickListener { onClick(movieId) }
    }
}

open class MovieHeaderHolder(val onClick: (category: String) -> Unit) :
    EpoxyModelWithHolder<HeaderHolder>() {

    @EpoxyAttribute
    lateinit var title: String

    override fun getDefaultLayout(): Int {
        return R.layout.movie_header_item
    }

    override fun createNewHolder(parent: ViewParent): HeaderHolder {
        return HeaderHolder()
    }

    override fun bind(holder: HeaderHolder) {
        holder.headerTitle.text = title
        holder.seeAll.setOnClickListener { onClick(title) }
    }
}

open class SubMovieHolder(private val onClick: (movieId: Int) -> Unit) : EpoxyModelWithHolder<SubHolder>() {

    @EpoxyAttribute
    lateinit var title: String

    @EpoxyAttribute
    lateinit var imageUrl: String

    @EpoxyAttribute
    var movieId: Int = 0

    override fun getDefaultLayout(): Int {
        return R.layout.sub_movie_item
    }

    override fun createNewHolder(parent: ViewParent): SubHolder {
        return SubHolder()
    }

    override fun bind(holder: SubHolder) {
        holder.title.text = title
        holder.image.loadMoviePoster(imageUrl)
        holder.card.setOnClickListener { onClick(movieId) }
    }

}

/* -------------------- Holders ------------------------ */

class MovieHolder : KotlinEpoxyHolder() {
    val title by bind<MaterialTextView>(R.id.movie_title_text)
    val image by bind<ImageView>(R.id.movie_img)
    val card by bind<MaterialCardView>(R.id.main_card_view)
}

class HeaderHolder : KotlinEpoxyHolder() {
    val headerTitle by bind<MaterialTextView>(R.id.movie_category_text)
    val seeAll by bind<MaterialTextView>(R.id.text_see_all)
}

class SubHolder : KotlinEpoxyHolder() {
    val title by bind<MaterialTextView>(R.id.sub_title_text)
    val image by bind<ImageView>(R.id.sub_img)
    val card by bind<MaterialCardView>(R.id.sub_card_view)
}