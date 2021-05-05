package com.kashyapkpatel.sampleapp.network.repo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.kashyapkpatel.sampleapp.network.api.MoviesApi
import com.kashyapkpatel.sampleapp.network.models.response.MovieListResponse
import com.kashyapkpatel.sampleapp.util.DispatcherProvider
import com.kashyapkpatel.sampleapp.util.Resource
import javax.inject.Inject

/**
 * This is a Main repo provided for MovieModule.
 * The coroutine context used here is dispatcherProvider.io()
 */
class DefaultMovieRepository @Inject constructor(
    override val context: Context,
    private val dispatcherProvider: DispatcherProvider,
    private val moviesApi: MoviesApi
) : BaseRepository(context), MovieRepository {

    /**
     * API to get Movies Listing.
     */
    override fun getMoviesList(): LiveData<Resource<MovieListResponse>> =
        liveData(dispatcherProvider.io()) {
            emit(Resource.loading())
            val result = safeApiCall { moviesApi.getMovieList() }
            emit(result)
        }

}