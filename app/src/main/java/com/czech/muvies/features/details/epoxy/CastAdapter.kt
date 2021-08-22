package com.czech.muvies.features.details.epoxy

import android.view.ViewParent
import android.widget.ImageView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.czech.muvies.R
import com.czech.muvies.utils.epoxy.KotlinEpoxyHolder
import com.czech.muvies.utils.loadMoviePoster
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView

open class CastAdapter(private val onClick:(castId: Int) -> Unit) : EpoxyModelWithHolder<CastAdapter.CastHolder>() {

    @EpoxyAttribute
    lateinit var imageUrl: String

    @EpoxyAttribute
    lateinit var castPlayName: String

    @EpoxyAttribute
    lateinit var castRealName: String

    @EpoxyAttribute
    var castId: Int = 0

    override fun getDefaultLayout(): Int {
        return R.layout.cast_item
    }

    override fun createNewHolder(parent: ViewParent): CastHolder {
        return CastHolder()
    }

    override fun bind(holder: CastHolder) {
        holder.apply {
            characterText.text = castPlayName
            nameText.text = castRealName
            castImage.loadMoviePoster(imageUrl)
            container.setOnClickListener { onClick(castId) }
        }
    }

    class CastHolder : KotlinEpoxyHolder() {
        val castImage by bind<ImageView>(R.id.m_cast_poster)
        val characterText by bind<MaterialTextView>(R.id.m_character)
        val nameText by bind<MaterialTextView>(R.id.m_name)
        val container by bind<MaterialCardView>(R.id.cast_item_container)
    }
}