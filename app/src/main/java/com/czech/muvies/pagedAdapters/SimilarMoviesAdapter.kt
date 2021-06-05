package com.czech.muvies.pagedAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.czech.muvies.R
import com.czech.muvies.databinding.SimilarListBinding
import com.czech.muvies.models.SimilarMovies
import com.czech.muvies.utils.loadMoviePoster

class SimilarMoviesAdapter(private val onClick:(movieId: Int) -> Unit):
        ListAdapter<SimilarMovies.SimilarMoviesResult, SimilarMoviesAdapter.SimilarMoviesViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarMoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.similar_list, parent, false)
        return SimilarMoviesViewHolder(SimilarListBinding.bind(view), onClick)
    }

    override fun onBindViewHolder(holder: SimilarMoviesViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<SimilarMovies.SimilarMoviesResult>() {

            override fun areItemsTheSame(oldItem: SimilarMovies.SimilarMoviesResult, newItem: SimilarMovies.SimilarMoviesResult): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SimilarMovies.SimilarMoviesResult, newItem: SimilarMovies.SimilarMoviesResult): Boolean {
                return oldItem == newItem
            }
        }
    }

    class SimilarMoviesViewHolder(private val binding: SimilarListBinding, private val onClick:(movieId: Int) -> Unit): RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: SimilarMovies.SimilarMoviesResult) {
            binding.poster.loadMoviePoster(movie.posterPath)
            binding.root.setOnClickListener { movie.id?.let { id -> onClick(id) } }
        }
    }
}
