package com.ekyrizky.core.ui.favorite.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ekyrizky.core.BuildConfig
import com.ekyrizky.core.R
import com.ekyrizky.core.databinding.ItemsListBinding
import com.ekyrizky.core.domain.model.movie.FavoriteMovieDomain
import com.ekyrizky.core.utils.ConvertUtils.getDateConverted
import com.ekyrizky.core.utils.ConvertUtils.getRuntimeConverted

class FavoriteMovieAdapter : ListAdapter<FavoriteMovieDomain, FavoriteMovieAdapter.ListViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteMovieDomain>() {
            override fun areItemsTheSame(oldItem: FavoriteMovieDomain, newItem: FavoriteMovieDomain): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FavoriteMovieDomain, newItem: FavoriteMovieDomain): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ListViewHolder(private val binding: ItemsListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movieItems: FavoriteMovieDomain) {
            with(binding) {
                tvTitle.text = movieItems.title
                tvReleaseYear.text = movieItems.releaseYear?.let { getDateConverted(it) }
                tvRuntime.text = movieItems.runtime?.let { getRuntimeConverted(it) }
                tvVoteAverage.text = movieItems.voteAverage.toString()
                Glide.with(itemView.context)
                    .load("${BuildConfig.BASE_IMG}${movieItems.posterPath}")
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                    .into(imgPoster)

                root.setOnClickListener { onItemClickListener?.let { it(movieItems.id.toString()) } }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
        ListViewHolder(ItemsListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }

    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }
}