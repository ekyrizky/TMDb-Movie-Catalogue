package com.ekyrizky.moviecatalogue.ui.home.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ekyrizky.moviecatalogue.core.data.Resource
import com.ekyrizky.moviecatalogue.core.domain.model.tvshow.TvShowDomain
import com.ekyrizky.moviecatalogue.core.domain.usecase.ContentUseCase
import com.ekyrizky.moviecatalogue.core.utils.SortUtils.NEWEST
import com.ekyrizky.moviecatalogue.tvshow.TvShowViewModel
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowViewModelTest {
    private val dummySize = 10
    private lateinit var viewModel: TvShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var contentRepository: ContentUseCase

    @Mock
    private lateinit var observer: Observer<Resource<List<TvShowDomain>>>

    @Mock
    private lateinit var pagedList: List<TvShowDomain>

    @Before
    fun setUp() {
        viewModel = TvShowViewModel(contentRepository)
    }

    @Test
    fun getListTvShows() {
        val dummyTvShow = Resource.Success(pagedList)
        `when`(dummyTvShow.data?.size).thenReturn(dummySize)
        val tvShows = MutableLiveData<Resource<List<TvShowDomain>>>()
        tvShows.value = dummyTvShow

        `when`(contentRepository.getTvShows(NEWEST)).thenReturn(tvShows)
        val tvShow = viewModel.getTvShows(NEWEST).value?.data
        verify(contentRepository).getTvShows(NEWEST)
        assertNotNull(tvShow)
        assertEquals(dummySize, tvShow?.size)

        viewModel.getTvShows(NEWEST).observeForever(observer)
        verify(observer).onChanged(dummyTvShow)
    }
}