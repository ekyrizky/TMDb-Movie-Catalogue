package com.ekyrizky.core.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ekyrizky.core.BuildConfig
import com.ekyrizky.core.R
import com.ekyrizky.core.databinding.ItemsGridHorizontalBinding
import com.ekyrizky.core.domain.model.tvshow.TvShowDomain

class SearchTvShowAdapter : RecyclerView.Adapter<SearchTvShowAdapter.SearchTvShowViewHolder>() {

    private var tvShowList = ArrayList<TvShowDomain>()

    fun setData(newList: List<TvShowDomain>?) {
        if (newList != null) {
            tvShowList.clear()
            tvShowList.addAll(newList)
            notifyDataSetChanged()
        }
    }

    var onItemClick: ((Int?) -> Unit)? = null

    inner class SearchTvShowViewHolder(private val binding: ItemsGridHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShowItems: TvShowDomain) {
            with(binding) {
                Glide.with(itemView.context)
                    .load("${BuildConfig.BASE_IMG}${tvShowItems.posterPath}")
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error))
                    .into(imgPoster)
                root.setOnClickListener { onItemClick?.invoke(tvShowItems.id) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchTvShowViewHolder =
        SearchTvShowViewHolder(ItemsGridHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: SearchTvShowViewHolder, position: Int) =
        holder.bind(tvShowList[position])


    override fun getItemCount(): Int = tvShowList.size
}