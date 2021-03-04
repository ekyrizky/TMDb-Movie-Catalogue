package com.ekyrizky.moviecatalogue.core.ui.tvshow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ekyrizky.moviecatalogue.BuildConfig.BASE_IMG
import com.ekyrizky.moviecatalogue.ContentCallback
import com.ekyrizky.moviecatalogue.R
import com.ekyrizky.moviecatalogue.core.domain.model.tvshow.TvShowDomain
import com.ekyrizky.moviecatalogue.core.utils.ConvertUtils.getDateConverted
import com.ekyrizky.moviecatalogue.databinding.ItemsGridBinding

class TvShowAdapter : ListAdapter<TvShowDomain, TvShowAdapter.TvShowViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowDomain>() {
            override fun areItemsTheSame(oldItem: TvShowDomain, newItem: TvShowDomain): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: TvShowDomain, newItem: TvShowDomain): Boolean =
                    oldItem == newItem
        }
    }

    private lateinit var onItemClickCallback: ContentCallback

    fun setOnItemClickCallback(onItemClickCallback: ContentCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class TvShowViewHolder(private val binding: ItemsGridBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShowItems: TvShowDomain) {
            with(binding) {
                tvTitle.text = tvShowItems.title
                tvReleaseYear.text = tvShowItems.releaseYear?.let { getDateConverted(it) }
                Glide.with(itemView.context)
                        .load("$BASE_IMG${tvShowItems.posterPath}")
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                                .error(R.drawable.ic_error))
                        .into(imgPoster)
                itemView.setOnClickListener { onItemClickCallback.onItemClicked(tvShowItems.id.toString()) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder =
            TvShowViewHolder(ItemsGridBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null) {
            holder.bind(tvShow)
        }
    }
}