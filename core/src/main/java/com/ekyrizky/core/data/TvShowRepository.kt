package com.ekyrizky.core.data

import com.ekyrizky.core.data.source.local.LocalDataSource
import com.ekyrizky.core.data.source.local.entity.tvshow.FavoriteTvShowEntity
import com.ekyrizky.core.data.source.remote.RemoteDataSource
import com.ekyrizky.core.data.source.remote.network.ApiResponse
import com.ekyrizky.core.data.source.remote.response.tvshow.TvShowDetailResponse
import com.ekyrizky.core.data.source.remote.response.tvshow.TvShowResultResponse
import com.ekyrizky.core.domain.model.tvshow.FavoriteTvShowDomain
import com.ekyrizky.core.domain.model.tvshow.TvShowDetailDomain
import com.ekyrizky.core.domain.model.tvshow.TvShowDomain
import com.ekyrizky.core.domain.repository.ITvShowRepository
import com.ekyrizky.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvShowRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
): ITvShowRepository {

    override fun getTvShows(sort: String): Flow<Resource<List<TvShowDomain>>> {
        return object : NetworkBoundResource<List<TvShowDomain>, List<TvShowResultResponse>>() {
            override fun loadFromDB(): Flow<List<TvShowDomain>> {
                return localDataSource.getTvShows(sort).map { DataMapper.mapTvShowEntityToDomain(it) }
            }

            override fun shouldFetch(data: List<TvShowDomain>?): Boolean =
                data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<TvShowResultResponse>>> =
                remoteDataSource.getTvShows()

            override suspend fun saveCallResult(data: List<TvShowResultResponse>) {
                val tvShowList = DataMapper.mapTvShowsResponseToEntity(data)
                localDataSource.insertTvShows(tvShowList)
            }
        }.asFlow()
    }

    override fun getTvShowDetail(tvShowId: Int): Flow<Resource<TvShowDetailDomain>> {
        return object : NetworkBoundResource<TvShowDetailDomain, TvShowDetailResponse>() {
            override fun loadFromDB(): Flow<TvShowDetailDomain> {
                return localDataSource.getTvShowById(tvShowId).map { DataMapper.mapTvShowDetailEntityToDomain(it) }
            }

            override fun shouldFetch(data: TvShowDetailDomain?): Boolean =
                data?.id == null

            override suspend fun createCall(): Flow<ApiResponse<TvShowDetailResponse>> =
                remoteDataSource.getTvShowDetail(tvShowId)

            override suspend fun saveCallResult(data: TvShowDetailResponse) {
                val tvShow = DataMapper.mapTvShowDetailResponseToEntity(data)
                localDataSource.insertTvShowDetail(tvShow)
            }
        }.asFlow()
    }

    override fun getFavoriteTvShows(): Flow<List<FavoriteTvShowDomain>> =
        localDataSource.getFavoriteTvShows().map { DataMapper.mapFavoriteTvShowEntityToDomain(it) }

    override suspend fun getSearchTvShows(query: String): Resource<List<TvShowDomain>> {
        return when (val apiResponse = remoteDataSource.getSearchTvShow(query).first()) {
            is ApiResponse.Success -> {
                val result = DataMapper.mapTvShowsResponseToDomain(apiResponse.data)
                Resource.Success(result)
            }
            is ApiResponse.Empty -> Resource.Error(apiResponse.toString())
            is ApiResponse.Error -> Resource.Error(apiResponse.errorMessage)
        }
    }

    override suspend fun insertFavoriteTvShow(favoriteTvShow: FavoriteTvShowEntity) =
        localDataSource.insertFavoriteTvShow(favoriteTvShow)

    override suspend fun checkFavoriteTvShow(id: Int): Boolean =
        localDataSource.checkFavoriteTvShow(id)

    override suspend fun deleteFavoriteTvShowById(id: Int) =
        localDataSource.deleteFavoriteTvShowById(id)

    override suspend fun deleteFavoriteTvShow(favoriteTvShow: FavoriteTvShowEntity) =
        localDataSource.deleteFavoriteTvShow(favoriteTvShow)
}