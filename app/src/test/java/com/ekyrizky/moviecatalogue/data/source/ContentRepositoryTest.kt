package com.ekyrizky.moviecatalogue.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ekyrizky.moviecatalogue.data.source.remote.RemoteDataSource
import com.ekyrizky.moviecatalogue.utils.DataDummy
import com.ekyrizky.moviecatalogue.utils.LiveDataTestUtil
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito

class ContentRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val contentRepository = FakeContentRepository(remote)

    private val listMovieResponse = DataDummy.generateRemoteDummyMovies()
    private val movieId = listMovieResponse[0].id
    private val listTvShowResponse = DataDummy.generateRemoteDummyTvShows()
    private val tvShowId = listTvShowResponse[0].id

    private val movieResponse = DataDummy.generateRemoteDummyMovies()[0]
    private val tvShowResponse = DataDummy.generateRemoteDummyTvShows()[0]

    @Test
    fun getNowPlayingMovies() {
        runBlocking {
            doAnswer {invocation ->
                (invocation.arguments[0] as RemoteDataSource.LoadNowPlayingMoviesCallback).onNowPlayingMoviesReceived(listMovieResponse)
                null
            }.`when`(remote).getNowPlayingMovies(any())
        }

        val data = LiveDataTestUtil.getValue(contentRepository.getNowPlayingMovies())

        runBlocking {
            verify(remote).getNowPlayingMovies(any())
        }

        assertNotNull(data)
        assertEquals(listMovieResponse.size.toLong(), data.size.toLong())
    }

    @Test
    fun getMovieDetail() {
        runBlocking {
            doAnswer {invocation ->
                (invocation.arguments[1] as RemoteDataSource.LoadMovieDetailCallback).onMovieDetailReceived(movieResponse)
                null
            }.`when`(remote).getMovieDetail(eq(movieId), any())
        }

        val data = LiveDataTestUtil.getValue(contentRepository.getMovieDetail(movieId))

        runBlocking {
            verify(remote).getMovieDetail(eq(movieId), any())
        }

        assertNotNull(data)
        assertEquals(movieResponse.id, data.id)
    }

    @Test
    fun getOnTheAirTvShows() {
        runBlocking {
            doAnswer { invocation ->
                (invocation.arguments[0] as RemoteDataSource.LoadOnTheAirTvShowsCallback).onOnAirTvShowsReceived(listTvShowResponse)
                null
            }.`when`(remote).getOnTheAirTvShows(any())
        }

        val data = LiveDataTestUtil.getValue(contentRepository.getOnTheAirTvShows())

        runBlocking {
            verify(remote).getOnTheAirTvShows(any())
        }

        assertNotNull(data)
        assertEquals(listTvShowResponse.size.toLong(), data.size.toLong())
    }

    @Test
    fun getTvShowDetail() {
        runBlocking {
            doAnswer {invocation ->
                (invocation.arguments[1] as RemoteDataSource.LoadTvShowDetailCallback).onTvShowDetailReceived(tvShowResponse)
                null
            }.`when`(remote).getTvShowDetail(eq(tvShowId), any())
        }

        val data = LiveDataTestUtil.getValue(contentRepository.getTvShowDetail(tvShowId))

        runBlocking {
            verify(remote).getTvShowDetail(eq(tvShowId), any())
        }

        assertNotNull(data)
        assertEquals(tvShowResponse.id, data.id)
    }
}