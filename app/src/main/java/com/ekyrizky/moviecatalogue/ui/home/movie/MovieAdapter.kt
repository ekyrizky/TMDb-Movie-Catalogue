package com.ekyrizky.moviecatalogue.ui.home.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ekyrizky.moviecatalogue.BuildConfig.BASE_IMG
import com.ekyrizky.moviecatalogue.R
import com.ekyrizky.moviecatalogue.data.source.local.entity.MovieEntity
import com.ekyrizky.moviecatalogue.databinding.ItemsGridBinding
import com.ekyrizky.moviecatalogue.ui.home.ContentCallback
import com.ekyrizky.moviecatalogue.utils.ConvertUtils.getDateConverted

class MovieAdapter : PagedListAdapter<MovieEntity, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean =
                oldItem == newItem
        }
    }

    private lateinit var onItemClickCallback: ContentCallback

    fun setOnItemClickCallback(onItemClickCallback: ContentCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class MovieViewHolder(private val binding: ItemsGridBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movieItems: MovieEntity) {
            with(binding) {
                tvTitle.text = movieItems.title
                tvReleaseYear.text = getDateConverted(movieItems.releaseYear.toString())
                Glide.with(itemView.context)
                    .load("$BASE_IMG${movieItems.posterPath}")
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                    .into(imgPoster)
                itemView.setOnClickListener { onItemClickCallback.onItemClicked(movieItems.id.toString()) }
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