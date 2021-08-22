package com.czech.muvies.features.details.epoxy

import android.view.ViewParent
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.czech.muvies.R
import com.czech.muvies.utils.epoxy.KotlinEpoxyHolder
import com.google.android.material.textview.MaterialTextView

open class GenreItemAdapter : EpoxyModelWithHolder<GenreItemAdapter.GenreItemHolder>() {

    @EpoxyAttribute
    lateinit var genre: String

    override fun getDefaultLayout(): Int {
        return R.layout.genre_item
    }

    override fun createNewHolder(parent: ViewParent): GenreItemHolder {
        return GenreItemHolder()
    }

    override fun bind(holder: GenreItemHolder) {
        holder.genreText.text = genre
    }

    class GenreItemHolder : KotlinEpoxyHolder() {
        val genreText by bind<MaterialTextView>(R.id.genre_text)
    }
}
