package com.ekyrizky.moviecatalogue.ui.home.favorite.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.ekyrizky.moviecatalogue.data.source.ContentRepository
import com.ekyrizky.moviecatalogue.data.source.local.entity.TvShowEntity
import com.ekyrizky.moviecatalogue.utils.DataDummy
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
    private lateinit var contentRepository: ContentRepository

    @Mock
    private lateinit var observer: Observer<PagedList<TvShowEntity>>

    @Mock
    private lateinit var pagedList: PagedList<TvShowEntity>

    @Before
    fun setUp() {
        viewModel = FavoriteTvShowViewModel(contentRepository)
    }
    @Test
    fun getFavoriteTvShow() {
        val dummyTvShow = pagedList
        Mockito.`when`(dummyTvShow.size).thenReturn(3)
        val tvShows = MutableLiveData<PagedList<TvShowEntity>>()
        tvShows.value = dummyTvShow

        Mockito.`when`(contentRepository.getFavoriteTvShows()).thenReturn(tvShows)
        val tvShow = viewModel.getFavoriteTvShow().value
        verify(contentRepository).getFavoriteTvShows()
        assertNotNull(tvShow)
        assertEquals(3, tvShow?.size)

        viewModel.getFavoriteTvShow().observeForever(observer)
        verify(observer).onChanged(dummyTvShow)
    }

    @Test
    fun setFavoriteTvShow() {
        viewModel.setFavoriteTvShow(DataDummy.generateDummyTvShowDetail())
        verify(contentRepository).setFavoriteTvShow(DataDummy.generateDummyTvShowDetail(), true)
        verifyNoMoreInteractions(contentRepository)
    }
}