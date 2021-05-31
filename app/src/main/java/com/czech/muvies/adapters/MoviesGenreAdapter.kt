package com.czech.muvies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.czech.muvies.R
import com.czech.muvies.databinding.GenreListBinding
import com.czech.muvies.models.MovieDetails

class MoviesGenreAdapter :
    ListAdapter<MovieDetails.Genre, MoviesGenreAdapter.MoviesGenreViewHolder>(MoviesGenreCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesGenreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.genre_list, parent, false)
        return MoviesGenreViewHolder(GenreListBinding.bind(view))
    }

    override fun onBindViewHolder(holder: MoviesGenreViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MoviesGenreViewHolder(val binding: GenreListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(genre: MovieDetails.Genre) {
            binding.genre.text = genre.name
        }
    }

    class MoviesGenreCallback : DiffUtil.ItemCallback<MovieDetails.Genre>() {
        override fun areItemsTheSame(
            oldItem: MovieDetails.Genre,
            newItem: MovieDetails.Genre
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MovieDetails.Genre,
            newItem: MovieDetails.Genre
        ): Boolean {
            return oldItem == newItem
        }
    }
}
