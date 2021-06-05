package com.czech.muvies.utils.epoxy

import android.content.Context
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.ModelView

@ModelView(autoLayout = ModelView.Size.WRAP_WIDTH_WRAP_HEIGHT, saveViewState = true)
open class CarouselNoSnap(context: Context) : Carousel(context) {
    override fun getSnapHelperFactory(): SnapHelperFactory? {
        return null
    }
}
