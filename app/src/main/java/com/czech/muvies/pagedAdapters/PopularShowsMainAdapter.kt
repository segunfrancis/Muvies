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
import com.czech.muvies.utils.AppConstants.BASE_IMAGE_PATH
import com.czech.muvies.R
import com.czech.muvies.models.TvShows
import kotlinx.android.synthetic.main.paged_list.view.*

typealias popularTvItemClickListener = (TvShows.TvShowsResult) -> Unit

class PopularShowsMainAdapter(private val clickListener: popularTvItemClickListener):
    PagedListAdapter<TvShows.TvShowsResult, PopularShowsMainAdapter.PopularShowsMainViewHolder>(diffUtil)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularShowsMainViewHolder {
        return PopularShowsMainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.paged_list, parent, false))
    }

    override fun onBindViewHolder(holder: PopularShowsMainViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<TvShows.TvShowsResult>() {
            override fun areItemsTheSame(oldItem: TvShows.TvShowsResult, newItem: TvShows.TvShowsResult): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShows.TvShowsResult, newItem: TvShows.TvShowsResult): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class PopularShowsMainViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {

        private var poster: ImageView = itemView.poster_image
        private var title: TextView = itemView.title
        private var date: TextView = itemView.date
        private var vote: TextView = itemView.vote

        fun bind(result: TvShows.TvShowsResult) {
            title.text = result.name
            date.text = result.firstAirDate
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
            val popular = getItem(adapterPosition)
            popular?.let { clickListener.invoke(it) }
        }

    }
}