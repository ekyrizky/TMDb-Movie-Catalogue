package com.ekyrizky.core.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ekyrizky.core.BuildConfig
import com.ekyrizky.core.R
import com.ekyrizky.core.databinding.ItemsGridHorizontalBinding
import com.ekyrizky.core.domain.model.movie.MovieDomain

class SearchMovieAdapter : RecyclerView.Adapter<SearchMovieAdapter.SearchMovieViewHolder>() {

    private var movieList = ArrayList<MovieDomain>()

    fun setData(newList: List<MovieDomain>?) {
        if (newList != null) {
            movieList.clear()
            movieList.addAll(newList)
            notifyDataSetChanged()
        }
    }

    inner class SearchMovieViewHolder(private val binding: ItemsGridHorizontalBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movieItems: MovieDomain) {
            with(binding) {
                Glide.with(itemView.context)
                        .load("${BuildConfig.BASE_IMG}${movieItems.posterPath}")
                        .apply(
                                RequestOptions.placeholderOf(R.drawable.ic_loading)
                                        .error(R.drawable.ic_error))
                        .into(imgPoster)
                root.setOnClickListener {
                    onItemClickListener?.let { it(movieItems.id.toString()) }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMovieViewHolder =
        SearchMovieViewHolder(ItemsGridHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: SearchMovieViewHolder, position: Int) =
        holder.bind(movieList[position])


    override fun getItemCount(): Int = movieList.size

    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }
}