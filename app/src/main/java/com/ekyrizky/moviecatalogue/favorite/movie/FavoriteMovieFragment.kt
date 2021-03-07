package com.ekyrizky.moviecatalogue.favorite.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ekyrizky.moviecatalogue.R
import com.ekyrizky.moviecatalogue.core.ui.ViewModelFactory
import com.ekyrizky.moviecatalogue.core.ui.favorite.movie.FavoriteMovieAdapter
import com.ekyrizky.moviecatalogue.databinding.FragmentFavoriteMovieBinding
import com.ekyrizky.moviecatalogue.favorite.FavoriteFragmentDirections
import com.google.android.material.snackbar.Snackbar

class FavoriteMovieFragment : Fragment() {
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
            val action = FavoriteFragmentDirections.actionNavigationFavoriteToNavigationMovieDetail()
            movieFavAdapter.onItemClick = {
                action.movieId = it.toString()
                view.findNavController().navigate(action)
            }
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
                val favoriteMovieDomain = movieFavAdapter.getSwipedData(swipedPosition)
                favoriteMovieDomain?.let { viewModel.deleteFavoriteMovie(it) }

                val snackBar = Snackbar.make(requireView(), getString(R.string.undo, favoriteMovieDomain?.title), Snackbar.LENGTH_LONG)
                snackBar.setAnchorView(R.id.nav_view)
                snackBar.setAction(R.string.ok) { _ ->
                    favoriteMovieDomain?.let { viewModel.insertFavoriteMovie(it) }
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.rvFavoriteMovie?.adapter = null
        _fragmentMovieFavoriteBinding = null
    }
}