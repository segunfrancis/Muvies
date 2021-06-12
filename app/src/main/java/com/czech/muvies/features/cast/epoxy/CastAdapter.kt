package com.czech.muvies.features.cast.epoxy

import android.view.ViewParent
import android.widget.ImageView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.czech.muvies.R
import com.czech.muvies.utils.epoxy.KotlinEpoxyHolder
import com.czech.muvies.utils.loadMoviePoster
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView

open class CastAdapter(private val onClick: (id: Int) -> Unit) : EpoxyModelWithHolder<CastAdapter.CastHolder>() {

    @EpoxyAttribute
    lateinit var name: String

    @EpoxyAttribute
    lateinit var imageUrl: String

    @EpoxyAttribute
    var itemId: Int = 0

    class CastHolder : KotlinEpoxyHolder() {
        val title by bind<MaterialTextView>(R.id.c_movie_title)
        val image by bind<ImageView>(R.id.c_poster)
        val card by bind<MaterialCardView>(R.id.c_container)
    }

    override fun getDefaultLayout(): Int {
        return R.layout.cast_other
    }

    override fun createNewHolder(parent: ViewParent): CastHolder {
        return CastHolder()
    }

    override fun bind(holder: CastHolder) {
        holder.title.text = name
        holder.image.loadMoviePoster(imageUrl)
        holder.card.setOnClickListener { onClick(itemId) }
    }
}
