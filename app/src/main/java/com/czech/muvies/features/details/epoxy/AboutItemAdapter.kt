package com.czech.muvies.features.details.epoxy

import android.view.ViewParent
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.czech.muvies.R
import com.czech.muvies.utils.epoxy.KotlinEpoxyHolder
import com.google.android.material.textview.MaterialTextView

open class AboutItemAdapter(private val onLinkClick: (url: String) -> Unit) :
    EpoxyModelWithHolder<AboutItemAdapter.AboutItemHolder>() {

    @EpoxyAttribute
    lateinit var originalTitle: String

    @EpoxyAttribute
    lateinit var runTime: String

    @EpoxyAttribute
    lateinit var status: String

    @EpoxyAttribute
    lateinit var releaseDate: String

    @EpoxyAttribute
    lateinit var voteCount: String

    @EpoxyAttribute
    lateinit var tagline: String

    @EpoxyAttribute
    lateinit var homePage: String

    override fun getDefaultLayout(): Int {
        return R.layout.about_item
    }

    override fun createNewHolder(parent: ViewParent): AboutItemHolder {
        return AboutItemHolder()
    }

    override fun bind(holder: AboutItemHolder) {
        holder.apply {
            originalTitleText.text = originalTitle
            runtimeText.text = runTime
            statusText.text = status
            releaseDateText.text = releaseDate
            voteCountText.text = voteCount
            taglineText.text = tagline
            homePageText.text = homePage
            homePageText.setOnClickListener { onLinkClick(homePage) }
            homePageText.highlightColor = ContextCompat.getColor(homePageText.context, R.color.colorPrimary)
        }
    }

    class AboutItemHolder : KotlinEpoxyHolder() {
        val originalTitleText by bind<MaterialTextView>(R.id.m_original_Title)
        val runtimeText by bind<MaterialTextView>(R.id.m_runtime)
        val statusText by bind<MaterialTextView>(R.id.m_status)
        val releaseDateText by bind<MaterialTextView>(R.id.m_release_date)
        val voteCountText by bind<MaterialTextView>(R.id.m_vote_count)
        val taglineText by bind<MaterialTextView>(R.id.m_tag_line)
        val homePageText by bind<MaterialTextView>(R.id.m_homepage)
    }
}
