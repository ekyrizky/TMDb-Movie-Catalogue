package com.ekyrizky.moviecatalogue.core.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ekyrizky.moviecatalogue.BuildConfig.BASE_IMG
import com.ekyrizky.moviecatalogue.R
import com.ekyrizky.moviecatalogue.core.domain.model.movie.MovieDomain
import com.ekyrizky.moviecatalogue.core.utils.ConvertUtils.getDateConverted
import com.ekyrizky.moviecatalogue.databinding.ItemsGridBinding

class MovieAdapter : ListAdapter<MovieDomain, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieDomain>() {
            override fun areItemsTheSame(oldItem: MovieDomain, newItem: MovieDomain): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MovieDomain, newItem: MovieDomain): Boolean =
                oldItem == newItem
        }
    }

    var onItemClick: ((Int?) -> Unit)? = null

    inner class MovieViewHolder(private val binding: ItemsGridBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movieItems: MovieDomain) {
            with(binding) {
                tvTitle.text = movieItems.title
                tvReleaseYear.text = movieItems.releaseYear?.let { getDateConverted(it) }
                Glide.with(itemView.context)
                    .load("$BASE_IMG${movieItems.posterPath}")
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                    .into(imgPoster)
                root.setOnClickListener { onItemClick?.invoke(movieItems.id) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(ItemsGridBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }
}