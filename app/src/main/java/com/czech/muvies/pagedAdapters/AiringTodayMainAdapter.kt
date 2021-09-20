package com.czech.muvies.pagedAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.czech.muvies.R
import com.czech.muvies.databinding.PagedListBinding
import com.czech.muvies.models.TvShows
import com.czech.muvies.utils.loadMoviePoster

class AiringTodayMainAdapter(private val clickListener: (Int) -> Unit) :
    PagedListAdapter<TvShows.TvShowsResult, AiringTodayMainAdapter.AiringTodayMainViewHolder>(
        diffUtil
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AiringTodayMainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.paged_list, parent, false)
        return AiringTodayMainViewHolder(PagedListBinding.bind(view))
    }

    override fun onBindViewHolder(holder: AiringTodayMainViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<TvShows.TvShowsResult>() {
            override fun areItemsTheSame(
                oldItem: TvShows.TvShowsResult,
                newItem: TvShows.TvShowsResult
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: TvShows.TvShowsResult,
                newItem: TvShows.TvShowsResult
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class AiringTodayMainViewHolder(private val binding: PagedListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: TvShows.TvShowsResult) = with(binding) {
            date.text = result.firstAirDate
            vote.text = result.voteAverage.toString()
            title.text = result.name
            posterImage.loadMoviePoster(result.posterPath)
        }

        init {
            itemView.setOnClickListener {
                getItem(adapterPosition)?.let { result -> clickListener(result.id) }
            }
        }
    }
}