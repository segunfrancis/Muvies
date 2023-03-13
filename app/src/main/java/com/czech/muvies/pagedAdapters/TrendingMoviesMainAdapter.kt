package com.czech.muvies.pagedAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.czech.muvies.R
import com.czech.muvies.models.Movies
import com.czech.muvies.utils.AppConstants.BASE_IMAGE_PATH

typealias trendingItemClickListener = (Movies.MoviesResult) -> Unit

class TrendingMoviesMainAdapter(private val clickListener: trendingItemClickListener):
    PagedListAdapter<Movies.MoviesResult, TrendingMoviesMainAdapter.TrendingMoviesMainViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingMoviesMainViewHolder {
        return TrendingMoviesMainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.paged_list, parent, false))
    }

    override fun onBindViewHolder(holder: TrendingMoviesMainViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Movies.MoviesResult>() {
            override fun areItemsTheSame(oldItem: Movies.MoviesResult, newItem: Movies.MoviesResult): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movies.MoviesResult, newItem: Movies.MoviesResult): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class TrendingMoviesMainViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {

        private var poster: ImageView = itemView.findViewById(R.id.poster_image)
        private var title: TextView = itemView.findViewById(R.id.title)
        private var date: TextView = itemView.findViewById(R.id.date)
        private var vote: TextView = itemView.findViewById(R.id.vote)

        fun bind(result: Movies.MoviesResult) {
            title.text = result.title
            date.text = result.releaseDate
            vote.text = result.voteAverage.toString()
            Glide.with(itemView)
                .load("$BASE_IMAGE_PATH${result.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .error(R.drawable.poster_error)
                .into(poster)
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val trending = getItem(adapterPosition)
            trending?.let { clickListener.invoke(it) }
        }
    }
}