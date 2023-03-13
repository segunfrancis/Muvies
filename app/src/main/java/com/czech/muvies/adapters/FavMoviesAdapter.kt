package com.czech.muvies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.czech.muvies.R
import com.czech.muvies.room.movies.MoviesEntity
import com.czech.muvies.utils.AppConstants.BASE_IMAGE_PATH
import com.czech.muvies.utils.convertDate

class FavMoviesAdapter(private var list: List<MoviesEntity>): RecyclerView.Adapter<FavMoviesAdapter.FavMoviesViewHolder>() {

    inner class FavMoviesViewHolder(inflater: LayoutInflater, parent: ViewGroup):
            RecyclerView.ViewHolder(inflater.inflate(R.layout.favorites_list, parent, false)) {

        private val poster: ImageView = itemView.findViewById(R.id.poster)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val date: TextView = itemView.findViewById(R.id.date)

                fun bind(movie: MoviesEntity) {

                    Glide.with(itemView)
                        .load("$BASE_IMAGE_PATH${movie.posterPath}")
                        .placeholder(R.drawable.backdrop_placeholder)
                        .into(poster)

                    title.text = movie.title

                    date.text = movie.releaseDate?.convertDate()
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavMoviesViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return FavMoviesViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: FavMoviesViewHolder, position: Int) {
        val movie: MoviesEntity = list[position]

        holder.bind(movie)
    }

    override fun getItemCount(): Int = list.size

    fun updateList(movieList: List<MoviesEntity>) {
        list = movieList
        notifyDataSetChanged()
    }
}