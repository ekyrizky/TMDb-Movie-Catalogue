package com.ekyrizky.moviecatalogue.ui.home.favorite.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ekyrizky.moviecatalogue.core.domain.model.tvshow.TvShowDomain
import com.ekyrizky.moviecatalogue.core.domain.usecase.ContentUseCase
import com.ekyrizky.moviecatalogue.core.utils.DataDummy
import com.ekyrizky.moviecatalogue.favorite.tvshow.FavoriteTvShowViewModel
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteTvShowViewModelTest {
    private val dummySize = 10
    private lateinit var viewModel: FavoriteTvShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var contentRepository: ContentUseCase

    @Mock
    private lateinit var observer: Observer<List<TvShowDomain>>

    @Mock
    private lateinit var pagedList: List<TvShowDomain>

    @Before
    fun setUp() {
        viewModel = FavoriteTvShowViewModel(contentRepository)
    }
    @Test
    fun getFavoriteTvShow() {
        val dummyTvShow = pagedList
        Mockito.`when`(dummyTvShow.size).thenReturn(dummySize)
        val tvShows = MutableLiveData<List<TvShowDomain>>()
        tvShows.value = dummyTvShow

        Mockito.`when`(contentRepository.getFavoriteTvShows()).thenReturn(tvShows)
        val tvShow = viewModel.getFavoriteTvShow().value
        verify(contentRepository).getFavoriteTvShows()
        assertNotNull(tvShow)
        assertEquals(dummySize, tvShow?.size)

        viewModel.getFavoriteTvShow().observeForever(observer)
        verify(observer).onChanged(dummyTvShow)
    }

    @Test
    fun setFavoriteTvShow() {
        viewModel.setFavoriteTvShow(DataDummy.generateDummyTvShowDomain())
        verify(contentRepository).setFavoriteTvShow(DataDummy.generateDummyTvShowDomain(), true)
        verifyNoMoreInteractions(contentRepository)
    }
}