package com.ekyrizky.core.ui.tvshow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ekyrizky.core.BuildConfig.BASE_IMG
import com.ekyrizky.core.R
import com.ekyrizky.core.databinding.ItemsGridBinding
import com.ekyrizky.core.domain.model.tvshow.TvShowDomain
import com.ekyrizky.core.utils.ConvertUtils.getDateConverted

class TvShowAdapter : ListAdapter<TvShowDomain, TvShowAdapter.TvShowViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowDomain>() {
            override fun areItemsTheSame(oldItem: TvShowDomain, newItem: TvShowDomain): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: TvShowDomain, newItem: TvShowDomain): Boolean =
                    oldItem == newItem
        }
    }

    var onItemClick: ((Int?) -> Unit)? = null

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
                root.setOnClickListener { onItemClick?.invoke(tvShowItems.id) }
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