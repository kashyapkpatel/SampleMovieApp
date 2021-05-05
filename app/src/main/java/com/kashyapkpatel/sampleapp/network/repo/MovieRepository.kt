package com.kashyapkpatel.sampleapp.network.repo

import androidx.lifecycle.LiveData
import com.kashyapkpatel.sampleapp.network.models.response.MovieListResponse
import com.kashyapkpatel.sampleapp.util.Resource

/**
 * This provides the great flexibility for the concrete repos to have different implementation.
 */
interface MovieRepository {

    fun getMoviesList(): LiveData<Resource<MovieListResponse>>

}