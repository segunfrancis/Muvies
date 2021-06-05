package com.czech.muvies.utils.epoxy

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.epoxy.ModelView

@ModelView(autoLayout = ModelView.Size.WRAP_WIDTH_WRAP_HEIGHT, saveViewState = true)
class GridCarouselNoSnap(private val mContext: Context) : CarouselNoSnap(mContext) {
    override fun createLayoutManager(): LayoutManager {
        return GridLayoutManager(mContext, 2, GridLayoutManager.HORIZONTAL, false)
    }
}
