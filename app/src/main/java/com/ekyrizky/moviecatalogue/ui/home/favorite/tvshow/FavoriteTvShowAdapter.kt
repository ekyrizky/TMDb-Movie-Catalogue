package com.ekyrizky.moviecatalogue.ui.home.favorite.tvshow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ekyrizky.moviecatalogue.BuildConfig
import com.ekyrizky.moviecatalogue.R
import com.ekyrizky.moviecatalogue.data.source.local.entity.TvShowEntity
import com.ekyrizky.moviecatalogue.databinding.ItemsListBinding
import com.ekyrizky.moviecatalogue.ui.home.ContentCallback
import com.ekyrizky.moviecatalogue.utils.ConvertUtils.getDateConverted
import com.ekyrizky.moviecatalogue.utils.ConvertUtils.getRuntimeConverted

class FavoriteTvShowAdapter : PagedListAdapter<TvShowEntity, FavoriteTvShowAdapter.ListViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowEntity>() {
            override fun areItemsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    private lateinit var onItemClickCallback: ContentCallback

    fun setOnItemClickCallback(onItemClickCallback: ContentCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun getSwipedData(swipedPosition: Int): TvShowEntity? = getItem(swipedPosition)

    inner class ListViewHolder(private val binding: ItemsListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShowItems: TvShowEntity) {
            with(binding) {
                tvTitle.text = tvShowItems.title
                tvReleaseYear.text = getDateConverted(tvShowItems.releaseYear.toString())
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