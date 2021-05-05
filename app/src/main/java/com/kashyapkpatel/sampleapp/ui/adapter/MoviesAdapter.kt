package com.kashyapkpatel.sampleapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kashyapkpatel.sampleapp.databinding.ItemMovieBinding
import com.kashyapkpatel.sampleapp.network.models.response.Movie

class MoviesAdapter : ListAdapter<Movie, MoviesAdapter.MovieViewHolder>(Companion) {

    var movieActionCallback: MovieActionCallback? = null
        get() = field        // getter
        set(value) {         // setter
            field = value
        }

    interface MovieActionCallback {
        fun onClicked(movie: Movie)
    }

    class MovieViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

    companion object: DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem === newItem
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.binding.movie = movie
        holder.binding.callback = movieActionCallback
        holder.binding.executePendingBindings()
    }
}