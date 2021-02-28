package com.ekyrizky.moviecatalogue.favorite.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ekyrizky.moviecatalogue.ContentCallback
import com.ekyrizky.moviecatalogue.R
import com.ekyrizky.moviecatalogue.core.ui.ViewModelFactory
import com.ekyrizky.moviecatalogue.core.ui.favorite.movie.FavoriteMovieAdapter
import com.ekyrizky.moviecatalogue.databinding.FragmentFavoriteMovieBinding
import com.ekyrizky.moviecatalogue.detail.DetailActivity
import com.ekyrizky.moviecatalogue.detail.DetailActivity.Companion.EXTRA_MOVIE
import com.google.android.material.snackbar.Snackbar

class FavoriteMovieFragment : Fragment(), ContentCallback {
    private var _fragmentMovieFavoriteBinding: FragmentFavoriteMovieBinding? = null
    private val binding get() = _fragmentMovieFavoriteBinding

    private lateinit var viewModel: FavoriteMovieViewModel
    private lateinit var movieFavAdapter: FavoriteMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentMovieFavoriteBinding = FragmentFavoriteMovieBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemTouchHelper.attachToRecyclerView(binding?.rvFavoriteMovie)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[FavoriteMovieViewModel::class.java]

            movieFavAdapter = FavoriteMovieAdapter()
            movieFavAdapter.setOnItemClickCallback(this)

            viewModel.getFavoriteMovies().observe(viewLifecycleOwner, { favMovies ->
                if (favMovies != null) {
                    binding?.rvFavoriteMovie?.adapter.let { adapter ->
                        when (adapter) {
                            is FavoriteMovieAdapter -> {
                                if (favMovies.isNullOrEmpty()) {
                                    binding?.rvFavoriteMovie?.isVisible = false
                                    binding?.imgEmpty?.isVisible = true
                                    binding?.tvEmpty?.isVisible = true
                                } else {
                                    binding?.rvFavoriteMovie?.isVisible = true
                                    binding?.imgEmpty?.isVisible = false
                                    binding?.tvEmpty?.isVisible = false
                                    adapter.submitList(favMovies)
                                    adapter.notifyDataSetChanged()
                                }
                            }
                        }
                    }
                }
            })

            initRecyclerView()
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val movieDomain = movieFavAdapter.getSwipedData(swipedPosition)
                movieDomain?.let { viewModel.setFavoriteMovie(it) }

                val snackBar = Snackbar.make(requireView(), getString(R.string.undo, movieDomain?.title), Snackbar.LENGTH_LONG)
                snackBar.setAction(R.string.ok) { _ ->
                    movieDomain?.let { viewModel.setFavoriteMovie(it) }
                }
                snackBar.show()
            }
        }
    })

    private fun initRecyclerView() {
        with(binding?.rvFavoriteMovie) {
            this?.layoutManager = LinearLayoutManager(context)
            this?.setHasFixedSize(true)
            this?.adapter = movieFavAdapter
        }
    }

    override fun onItemClicked(id: String) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_ID, id)
        intent.putExtra(DetailActivity.EXTRA_CONTENT, EXTRA_MOVIE)

        context?.startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavoriteMovies().observe(viewLifecycleOwner, { favMovies ->
            if (favMovies != null) {
                movieFavAdapter.submitList(favMovies)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentMovieFavoriteBinding = null
    }
}