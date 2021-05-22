package com.czech.muvies.utils.epoxy

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyModel
import com.czech.muvies.BuildConfig

abstract class BaseEpoxyController<T> : EpoxyController() {

    init {
        if (BuildConfig.DEBUG) {
            addInterceptor { requireUniqueModelIds(it) }
        }
    }

    var data: List<T>? = null
        set(value) {
            field = value
            requestModelBuild()
        }

    protected open val recycleChildrenOnDetach: Boolean = true

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        (recyclerView.layoutManager as? LinearLayoutManager)?.recycleChildrenOnDetach = recycleChildrenOnDetach
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        cancelPendingModelBuild()
    }
}

fun EpoxyController.requireUniqueModelIds(models: List<EpoxyModel<*>>) {
    val allIds = models.map { it.id() }
    val uniqueIds = allIds.distinct()
    require(uniqueIds.size == allIds.size) {
        val dupClasses = models.toMutableList()
            .apply {
                uniqueIds.map { id -> models.first { it.id() == id } }.forEach { remove(it) }
            }
            .map { it::class.java }
        "Found Epoxy models with duplicate ids in ${this::class.java}: $dupClasses!"
    }
}