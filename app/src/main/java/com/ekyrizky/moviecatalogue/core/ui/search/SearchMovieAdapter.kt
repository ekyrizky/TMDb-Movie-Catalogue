package com.ekyrizky.moviecatalogue.core.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ekyrizky.moviecatalogue.BuildConfig
import com.ekyrizky.moviecatalogue.R
import com.ekyrizky.moviecatalogue.core.domain.model.movie.MovieDomain
import com.ekyrizky.moviecatalogue.core.utils.ConvertUtils
import com.ekyrizky.moviecatalogue.databinding.ItemsGridBinding

class SearchMovieAdapter : RecyclerView.Adapter<SearchMovieAdapter.SearchMovieViewHolder>() {

    private var movieList = ArrayList<MovieDomain>()

    fun setData(newList: List<MovieDomain>?) {
        if (newList != null) {
            movieList.clear()
            movieList.addAll(newList)
            notifyDataSetChanged()
        }
    }

    inner class SearchMovieViewHolder(private val binding: ItemsGridBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movieItems: MovieDomain) {
            with(binding) {
                tvTitle.text = movieItems.title
                tvReleaseYear.text = movieItems.releaseYear?.let { ConvertUtils.getDateConverted(it) }
                Glide.with(itemView.context)
                        .load("${BuildConfig.BASE_IMG}${movieItems.posterPath}")
                        .apply(
                                RequestOptions.placeholderOf(R.drawable.ic_loading)
                                        .error(R.drawable.ic_error))
                        .into(imgPoster)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMovieViewHolder =
        SearchMovieViewHolder(ItemsGridBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: SearchMovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int = movieList.size


}