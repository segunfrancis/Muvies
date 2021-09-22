package com.czech.muvies.pagedAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.czech.muvies.R
import com.czech.muvies.databinding.PagedListBinding
import com.czech.muvies.models.Movies
import com.czech.muvies.utils.loadMoviePoster

class AllMoviesMainAdapter(private val clickListener: (Int, String) -> Unit) :
    PagedListAdapter<Movies.MoviesResult, AllMoviesMainAdapter.AiringTodayMainViewHolder>(
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
        val diffUtil = object : DiffUtil.ItemCallback<Movies.MoviesResult>() {
            override fun areItemsTheSame(
                oldItem: Movies.MoviesResult,
                newItem: Movies.MoviesResult
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Movies.MoviesResult,
                newItem: Movies.MoviesResult
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class AiringTodayMainViewHolder(private val binding: PagedListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Movies.MoviesResult) = with(binding) {
            date.text = result.releaseDate
            vote.text = result.voteAverage.toString()
            title.text = result.title
            posterImage.loadMoviePoster(result.posterPath)
        }

        init {
            itemView.setOnClickListener {
                getItem(adapterPosition)?.let { result -> clickListener(result.id, result.title) }
            }
        }
    }
}