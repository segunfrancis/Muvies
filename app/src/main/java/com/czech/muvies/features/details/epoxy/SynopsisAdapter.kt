package com.czech.muvies.features.details.epoxy

import android.view.ViewParent
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.czech.muvies.R
import com.czech.muvies.utils.epoxy.KotlinEpoxyHolder
import com.google.android.material.textview.MaterialTextView

open class SynopsisAdapter : EpoxyModelWithHolder<SynopsisAdapter.SynopsisHolder>() {

    @EpoxyAttribute
    lateinit var synopsis: String

    override fun getDefaultLayout(): Int {
        return R.layout.movie_info_item
    }

    override fun createNewHolder(parent: ViewParent): SynopsisHolder {
        return SynopsisHolder()
    }

    override fun bind(holder: SynopsisHolder) {
        holder.overviewTest.text = synopsis
    }

    class SynopsisHolder : KotlinEpoxyHolder() {
        val overviewTest by bind<MaterialTextView>(R.id.m_overview)
    }
}
