package com.czech.muvies.features.movie_details.epoxy

import android.view.ViewParent
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.czech.muvies.R
import com.czech.muvies.utils.epoxy.KotlinEpoxyHolder
import com.google.android.material.textview.MaterialTextView

open class SectionHeaderAdapter : EpoxyModelWithHolder<SectionHeaderAdapter.SectionHeaderHolder>() {

    @EpoxyAttribute
    lateinit var sectionTitle: String

    override fun createNewHolder(parent: ViewParent): SectionHeaderHolder {
        return SectionHeaderHolder()
    }

    override fun getDefaultLayout(): Int {
        return R.layout.section_header_item
    }

    override fun bind(holder: SectionHeaderHolder) {
        holder.sectionText.text = sectionTitle
    }

    class SectionHeaderHolder : KotlinEpoxyHolder() {
        val sectionText by bind<MaterialTextView>(R.id.text_section_header)
    }
}