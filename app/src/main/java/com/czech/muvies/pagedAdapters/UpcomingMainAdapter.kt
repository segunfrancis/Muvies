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
import com.czech.muvies.models.Movies
import kotlinx.android.synthetic.main.paged_list.view.*

typealias upcomingItemClickListener = (Movies.MoviesResult) -> Unit

class UpcomingMainAdapter(private val clickListener: upcomingItemClickListener):
    PagedListAdapter<Movies.MoviesResult, UpcomingMainAdapter.UpcomingMainViewHolder>(diffUtilCallBack){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingMainViewHolder {
        return UpcomingMainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.paged_list, parent, false))
    }

    override fun onBindViewHolder(holder: UpcomingMainViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val diffUtilCallBack = object : DiffUtil.ItemCallback<Movies.MoviesResult>()  {
            override fun areItemsTheSame(oldItem: Movies.MoviesResult, newItem: Movies.MoviesResult): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movies.MoviesResult, newItem: Movies.MoviesResult): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class UpcomingMainViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {

        private var poster: ImageView = itemView.poster_image
        private var title: TextView = itemView.title
        private var date: TextView = itemView.date
        private var vote: TextView = itemView.vote

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
            val upcoming = getItem(adapterPosition)
            upcoming?.let {
                clickListener.invoke(it)
            }
        }
    }
}