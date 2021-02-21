package com.ekyrizky.moviecatalogue.ui.home.favorite.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ekyrizky.moviecatalogue.BuildConfig
import com.ekyrizky.moviecatalogue.R
import com.ekyrizky.moviecatalogue.data.source.local.entity.MovieEntity
import com.ekyrizky.moviecatalogue.databinding.ItemsListBinding
import com.ekyrizky.moviecatalogue.ui.home.ContentCallback
import com.ekyrizky.moviecatalogue.utils.ConvertUtils
import com.ekyrizky.moviecatalogue.utils.ConvertUtils.getDateConverted

class FavoriteMovieAdapter : PagedListAdapter<MovieEntity, FavoriteMovieAdapter.ListViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    private lateinit var onItemClickCallback: ContentCallback

    fun setOnItemClickCallback(onItemClickCallback: ContentCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun getSwipedData(swipedPosition: Int): MovieEntity? = getItem(swipedPosition)

    inner class ListViewHolder(private val binding: ItemsListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movieItems: MovieEntity) {
            with(binding) {
                tvTitle.text = movieItems.title
                tvReleaseYear.text = getDateConverted(movieItems.releaseYear.toString())
                tvRuntime.text = movieItems.runtime?.let { ConvertUtils.getRuntimeConverted(it) }
                tvVoteAverage.text = movieItems.voteAverage.toString()
                Glide.with(itemView.context)
                    .load("${BuildConfig.BASE_IMG}${movieItems.posterPath}")
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                    .into(imgPoster)

                cardContent.setOnClickListener { onItemClickCallback.onItemClicked(movieItems.id.toString()) }
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
}