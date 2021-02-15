package com.ekyrizky.moviecatalogue.ui.content.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ekyrizky.moviecatalogue.data.source.ContentRepository
import com.ekyrizky.moviecatalogue.data.source.remote.response.MovieResponse
import com.ekyrizky.moviecatalogue.utils.DataDummy
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {
    private val dummyMovies = DataDummy.generateRemoteDummyMovies()
    private val dummySize = 10
    private lateinit var viewModel: MovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var contentRepository: ContentRepository

    @Mock
    private lateinit var observer: Observer<List<MovieResponse>>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(contentRepository)
    }

    @Test
    fun getListMovies() {
        val movies = MutableLiveData<List<MovieResponse>>()
        movies.value = dummyMovies

        `when`(contentRepository.getNowPlayingMovies()).thenReturn(movies)
        val contentEntities = viewModel.getListMovies().value
        verify(contentRepository).getNowPlayingMovies()
        assertNotNull(contentEntities)
        assertEquals(dummySize, contentEntities?.size)

        viewModel.getListMovies().observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }
}