package com.ekyrizky.moviecatalogue.ui.content.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ekyrizky.moviecatalogue.BuildConfig
import com.ekyrizky.moviecatalogue.R
import com.ekyrizky.moviecatalogue.data.source.remote.response.MovieResponse
import com.ekyrizky.moviecatalogue.databinding.ItemsGridBinding
import com.ekyrizky.moviecatalogue.ui.detail.DetailActivity
import java.text.SimpleDateFormat
import java.util.*

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ListViewHolder>() {
    private var listMovies = mutableListOf<MovieResponse>()

    fun setItems(movieItems: MutableList<MovieResponse>) {
        this.listMovies.clear()
        this.listMovies.addAll(movieItems)
        notifyDataSetChanged()
    }

    private fun dateConverter(date: String): String?{
        val apiDate = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val formatDate = SimpleDateFormat("yyyy", Locale.getDefault())
        val year = apiDate.parse(date)
        return year?.let { formatDate.format(it).orEmpty() }
    }

    inner class ListViewHolder(private val binding: ItemsGridBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movieItems: MovieResponse) {
            val imgSize = "w500"
            val imgUrl = BuildConfig.BASE_IMG

            with(binding) {
                tvTitle.text = movieItems.originalTitle
                tvReleaseYear.text = dateConverter(movieItems.releaseDate)
                Glide.with(itemView.context)
                        .load("$imgUrl$imgSize${listMovies[adapterPosition].posterPath}")
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                                .error(R.drawable.ic_error))
                        .into(imgPoster)
                cardContent.setOnClickListener {
                    Intent(it.context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.EXTRA_CONTENT, movieItems.id)
                        putExtra(DetailActivity.EXTRA_TYPE, DetailActivity.EXTRA_MOVIE)
                    }.also { intent ->
                        it.context.startActivity(intent)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
            ListViewHolder(ItemsGridBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) = holder.bind(listMovies[position])

    override fun getItemCount(): Int = listMovies.size
}
