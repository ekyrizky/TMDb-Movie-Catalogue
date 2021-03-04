package com.ekyrizky.moviecatalogue.core.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ekyrizky.moviecatalogue.BuildConfig
import com.ekyrizky.moviecatalogue.R
import com.ekyrizky.moviecatalogue.core.domain.model.tvshow.TvShowDomain
import com.ekyrizky.moviecatalogue.core.utils.ConvertUtils
import com.ekyrizky.moviecatalogue.databinding.ItemsGridBinding

class SearchTvShowAdapter : RecyclerView.Adapter<SearchTvShowAdapter.SearchTvShowViewHolder>() {

    private var tvShowList = ArrayList<TvShowDomain>()

    fun setData(newList: List<TvShowDomain>?) {
        if (newList != null) {
            tvShowList.clear()
            tvShowList.addAll(newList)
            notifyDataSetChanged()
        }
    }

    inner class SearchTvShowViewHolder(private val binding: ItemsGridBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShowItems: TvShowDomain) {
            with(binding) {
                tvTitle.text = tvShowItems.title
                tvReleaseYear.text = tvShowItems.releaseYear?.let { ConvertUtils.getDateConverted(it) }
                Glide.with(itemView.context)
                    .load("${BuildConfig.BASE_IMG}${tvShowItems.posterPath}")
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error))
                    .into(imgPoster)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchTvShowViewHolder =
        SearchTvShowViewHolder(ItemsGridBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: SearchTvShowViewHolder, position: Int) {
        holder.bind(tvShowList[position])
    }

    override fun getItemCount(): Int = tvShowList.size
}