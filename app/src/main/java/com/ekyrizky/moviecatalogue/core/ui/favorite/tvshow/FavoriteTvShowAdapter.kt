package com.ekyrizky.moviecatalogue.core.ui.favorite.tvshow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ekyrizky.moviecatalogue.BuildConfig
import com.ekyrizky.moviecatalogue.ContentCallback
import com.ekyrizky.moviecatalogue.R
import com.ekyrizky.moviecatalogue.core.domain.model.tvshow.FavoriteTvShowDomain
import com.ekyrizky.moviecatalogue.core.utils.ConvertUtils.getDateConverted
import com.ekyrizky.moviecatalogue.core.utils.ConvertUtils.getRuntimeConverted
import com.ekyrizky.moviecatalogue.databinding.ItemsListBinding

class FavoriteTvShowAdapter : ListAdapter<FavoriteTvShowDomain, FavoriteTvShowAdapter.ListViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteTvShowDomain>() {
            override fun areItemsTheSame(oldItem: FavoriteTvShowDomain, newItem: FavoriteTvShowDomain): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FavoriteTvShowDomain, newItem: FavoriteTvShowDomain): Boolean {
                return oldItem == newItem
            }
        }
    }

    private lateinit var onItemClickCallback: ContentCallback

    fun setOnItemClickCallback(onItemClickCallback: ContentCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun getSwipedData(swipedPosition: Int): FavoriteTvShowDomain? = getItem(swipedPosition)

    inner class ListViewHolder(private val binding: ItemsListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShowItems: FavoriteTvShowDomain) {
            with(binding) {
                tvTitle.text = tvShowItems.title
                tvReleaseYear.text = tvShowItems.releaseYear?.let { getDateConverted(it) }
                tvRuntime.text = tvShowItems.runtime?.let { getRuntimeConverted(it) }
                tvVoteAverage.text = tvShowItems.voteAverage.toString()
                Glide.with(itemView.context)
                        .load("${BuildConfig.BASE_IMG}${tvShowItems.posterPath}")
                        .apply(
                                RequestOptions.placeholderOf(R.drawable.ic_loading)
                                        .error(R.drawable.ic_error))
                        .into(imgPoster)

                cardContent.setOnClickListener { onItemClickCallback.onItemClicked(tvShowItems.id.toString()) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
            ListViewHolder(ItemsListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null) {
            holder.bind(tvShow)
        }
    }
}