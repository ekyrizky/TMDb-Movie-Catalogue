package com.ekyrizky.moviecatalogue.ui.home.tvshow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ekyrizky.moviecatalogue.BuildConfig.BASE_IMG
import com.ekyrizky.moviecatalogue.R
import com.ekyrizky.moviecatalogue.data.source.local.entity.TvShowEntity
import com.ekyrizky.moviecatalogue.databinding.ItemsGridBinding
import com.ekyrizky.moviecatalogue.ui.home.ContentCallback
import com.ekyrizky.moviecatalogue.utils.ConvertUtils.getDateConverted

class TvShowAdapter : PagedListAdapter<TvShowEntity, TvShowAdapter.TvShowViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowEntity>() {
            override fun areItemsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean =
                    oldItem == newItem
        }
    }

    private lateinit var onItemClickCallback: ContentCallback

    fun setOnItemClickCallback(onItemClickCallback: ContentCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class TvShowViewHolder(private val binding: ItemsGridBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShowItems: TvShowEntity) {
            with(binding) {
                tvTitle.text = tvShowItems.title
                tvReleaseYear.text = getDateConverted(tvShowItems.releaseYear.toString())
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