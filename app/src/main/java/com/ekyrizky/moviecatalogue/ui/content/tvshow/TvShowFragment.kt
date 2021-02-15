package com.ekyrizky.moviecatalogue.ui.content.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ekyrizky.moviecatalogue.data.source.remote.response.TvShowResponse
import com.ekyrizky.moviecatalogue.databinding.FragmentTvShowBinding
import com.ekyrizky.moviecatalogue.viewmodel.ViewModelFactory

class TvShowFragment : Fragment() {

    private lateinit var tvShowBinding: FragmentTvShowBinding
    private lateinit var viewModel: TvShowViewModel
    private val tvShowAdapter = TvShowAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        tvShowBinding = FragmentTvShowBinding.inflate(layoutInflater, container, false)
        return tvShowBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance()
            activity?.let {
                viewModel = ViewModelProvider(it, factory)[TvShowViewModel::class.java]
            }
        }

        getTvShowList()
        initRecyclerView()
    }

    private fun getTvShowList() {
        viewModel.getListTvShows().observe(viewLifecycleOwner, { tvShowItems ->
            tvShowAdapter.setItems(tvShowItems as MutableList<TvShowResponse>)
        })
    }

    private fun initRecyclerView() {
        with(tvShowBinding.rvTvShow) {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = tvShowAdapter
        }
    }
}