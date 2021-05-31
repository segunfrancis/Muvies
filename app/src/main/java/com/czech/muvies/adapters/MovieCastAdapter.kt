package com.czech.muvies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.czech.muvies.R
import com.czech.muvies.databinding.CastListBinding
import com.czech.muvies.models.MovieCredits
import com.czech.muvies.utils.loadMoviePoster

class MovieCastAdapter(private val onClick: (id: Int) -> Unit):
        ListAdapter<MovieCredits.Cast, MovieCastAdapter.MovieCastViewHolder>(MovieCastItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cast_list, parent, false)
        return MovieCastViewHolder(CastListBinding.bind(view), onClick)
    }

    override fun onBindViewHolder(holder: MovieCastViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MovieCastItemCallback : DiffUtil.ItemCallback<MovieCredits.Cast>() {
        override fun areItemsTheSame(
            oldItem: MovieCredits.Cast,
            newItem: MovieCredits.Cast
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MovieCredits.Cast,
            newItem: MovieCredits.Cast
        ): Boolean {
            return oldItem == newItem
        }

    }

    class MovieCastViewHolder(private val binding: CastListBinding, private val onClick: (movieId: Int) -> Unit): RecyclerView.ViewHolder(binding.root) {

        fun bind(cast: MovieCredits.Cast) {
            cast.profilePath?.let { binding.castPoster.loadMoviePoster(it) }
            binding.character.text = cast.character
            binding.name.text = cast.name
            binding.root.setOnClickListener { cast.id?.let { id -> onClick(id) } }
        }
    }
}
