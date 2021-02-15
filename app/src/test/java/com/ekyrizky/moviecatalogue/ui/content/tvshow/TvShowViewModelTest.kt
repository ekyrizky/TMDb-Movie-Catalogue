package com.ekyrizky.moviecatalogue.ui.content.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ekyrizky.moviecatalogue.data.source.ContentRepository
import com.ekyrizky.moviecatalogue.data.source.local.entity.ContentEntity
import com.ekyrizky.moviecatalogue.data.source.remote.response.TvShowResponse
import com.ekyrizky.moviecatalogue.utils.DataDummy
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowViewModelTest {
    private val dummyTvShows = DataDummy.generateRemoteDummyTvShows()
    private val dummySize = 10
    private lateinit var viewModel: TvShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var contentRepository: ContentRepository

    @Mock
    private lateinit var observer: Observer<List<TvShowResponse>>

    @Before
    fun setUp() {
        viewModel = TvShowViewModel(contentRepository)
    }

    @Test
    fun getListTvShows() {
        val tvShows = MutableLiveData<List<TvShowResponse>>()
        tvShows.value = dummyTvShows

        `when`(contentRepository.getOnTheAirTvShows()).thenReturn(tvShows)
        val contentEntities = viewModel.getListTvShows().value
        verify(contentRepository).getOnTheAirTvShows()
        assertNotNull(contentEntities)
        assertEquals(dummySize, contentEntities?.size)

        viewModel.getListTvShows().observeForever(observer)
        verify(observer).onChanged(dummyTvShows)
    }
}