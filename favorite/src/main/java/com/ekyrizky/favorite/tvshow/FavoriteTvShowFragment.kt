package com.ekyrizky.favorite.tvshow

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ekyrizky.core.ui.favorite.tvshow.FavoriteTvShowAdapter
import com.ekyrizky.favorite.FavoriteFragmentDirections
import com.ekyrizky.favorite.R
import com.ekyrizky.favorite.databinding.FragmentFavoriteTvShowBinding
import com.ekyrizky.favorite.di.DaggerFavoriteComponent
import com.ekyrizky.favorite.utils.DataMapper
import com.ekyrizky.favorite.utils.ViewModelFactory
import com.ekyrizky.moviecatalogue.di.FavoriteModuleDependencies
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteTvShowFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: FavoriteTvShowViewModel by viewModels { factory }

    private var _fragmentTvShowFavoriteBinding: FragmentFavoriteTvShowBinding? = null
    private val binding get() = _fragmentTvShowFavoriteBinding

    private lateinit var tvShowFavAdapter: FavoriteTvShowAdapter

    override fun onAttach(context: Context) {
        DaggerFavoriteComponent.builder()
            .context(context)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    context.applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentTvShowFavoriteBinding = FragmentFavoriteTvShowBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemTouchHelper.attachToRecyclerView(binding?.rvFavoriteTvshow)

        if (activity != null) {
            tvShowFavAdapter = FavoriteTvShowAdapter()

            val action = FavoriteFragmentDirections.actionNavigationFavoriteToNavigationTvshowDetail()
            tvShowFavAdapter.onItemClick = {
                action.tvShowId = it.toString()
                view.findNavController().navigate(action)
            }

            viewModel.getFavoriteTvShow().observe(viewLifecycleOwner) { favTvShow ->
                if (favTvShow != null) {
                    binding?.rvFavoriteTvshow?.adapter.let { adapter ->
                        when (adapter) {
                            is FavoriteTvShowAdapter -> {
                                if (favTvShow.isNullOrEmpty()) {
                                    binding?.rvFavoriteTvshow?.isVisible = false
                                    binding?.imgEmpty?.isVisible = true
                                    binding?.tvEmpty?.isVisible = true
                                } else {
                                    binding?.rvFavoriteTvshow?.isVisible = true
                                    binding?.imgEmpty?.isVisible = false
                                    binding?.tvEmpty?.isVisible = false
                                    val favoriteTvShow = DataMapper.mapFavoriteTvShowToFavoriteTvShowDomain(favTvShow)
                                    adapter.submitList(favoriteTvShow)
                                    adapter.notifyDataSetChanged()
                                }
                            }
                        }
                    }
                }
            }

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
                val tvShowDomain = tvShowFavAdapter.getSwipedData(swipedPosition)
                tvShowDomain?.let { viewModel.deleteFavoriteTvShow(it) }

                val snackBar = Snackbar.make(requireView(), getString(R.string.undo, tvShowDomain?.title), Snackbar.LENGTH_LONG)
                    .setAnchorView(com.ekyrizky.moviecatalogue.R.id.nav_view)
                    .setAction(R.string.ok) { _ -> tvShowDomain?.let { viewModel.insertFavoriteTvShow(it) } }
                snackBar.show()
            }
        }
    })

    private fun initRecyclerView() {
        with(binding?.rvFavoriteTvshow) {
            this?.layoutManager = LinearLayoutManager(context)
            this?.setHasFixedSize(true)
            this?.adapter = tvShowFavAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentTvShowFavoriteBinding = null
    }
}