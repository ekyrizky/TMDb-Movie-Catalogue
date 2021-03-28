package com.ekyrizky.favorite.movie

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ekyrizky.core.ui.favorite.movie.FavoriteMovieAdapter
import com.ekyrizky.favorite.databinding.FragmentFavoriteMovieBinding
import com.ekyrizky.favorite.di.DaggerFavoriteComponent
import com.ekyrizky.favorite.utils.DataMapper
import com.ekyrizky.favorite.utils.ViewModelFactory
import com.ekyrizky.moviecatalogue.di.FavoriteModuleDependencies
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteMovieFragment : Fragment() {

    private var _fragmentMovieFavoriteBinding: FragmentFavoriteMovieBinding? = null
    private val binding get() = _fragmentMovieFavoriteBinding

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: FavoriteMovieViewModel by viewModels { factory }

    private var _movieFavAdapter: FavoriteMovieAdapter? = null
    private val movieFavAdapter get() = _movieFavAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerFavoriteComponent.builder()
            .context(requireContext())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireActivity().applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)

        _movieFavAdapter = FavoriteMovieAdapter()

        movieFavAdapter?.setOnItemClickListener {
            val action =NavDeepLinkRequest.Builder
                .fromUri(Uri.parse("moviecatalogue://detail/$it"))
                .build()

            findNavController().navigate(action)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentMovieFavoriteBinding = FragmentFavoriteMovieBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        observeFavoriteMovies()
    }

    private fun observeFavoriteMovies() {
        viewModel.getFavoriteMovies().observe(viewLifecycleOwner, { favMovies ->
            if (favMovies != null) {
                binding?.rvFavoriteMovie?.adapter.let { adapter ->
                    when (adapter) {
                        is FavoriteMovieAdapter -> {
                            if (favMovies.isNullOrEmpty()) {
                                emptyFavorite(true)
                            } else {
                                emptyFavorite(false)
                                val favoriteMovie = DataMapper.mapFavoriteMovieToFavoriteMovieDomain(favMovies)
                                adapter.submitList(favoriteMovie)
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }
        })
    }

    private fun initRecyclerView() {
        with(binding?.rvFavoriteMovie) {
            this?.layoutManager = LinearLayoutManager(context)
            this?.setHasFixedSize(true)
            this?.adapter = movieFavAdapter
        }
    }

    private fun emptyFavorite(state: Boolean) {
        binding?.apply {
            imgEmpty.isVisible = state
            tvEmpty.isVisible = state
            rvFavoriteMovie.isVisible = !state
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        with(binding?.rvFavoriteMovie) {
            if (this?.adapter != null) {
                this.adapter = null
            }
        }
        _fragmentMovieFavoriteBinding = null
    }
}