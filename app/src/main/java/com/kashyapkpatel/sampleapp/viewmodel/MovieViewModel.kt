package com.kashyapkpatel.sampleapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

import com.kashyapkpatel.sampleapp.network.models.response.MovieListResponse
import com.kashyapkpatel.sampleapp.network.repo.MovieRepository
import com.kashyapkpatel.sampleapp.util.Resource
import javax.inject.Inject

/**
 * Common ViewModel class for Movies Module.
 * As we are using coroutines with livedata which is introduced as a part of lifecycle-runtime-ktx artifact in 2.2.0,
 * Our ViewModel is very simple due to livedata lifecycle awareness.
 * It has only MovieRepository as a dependency in order to provide data.
 */
class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    fun getMoviesList(): LiveData<Resource<MovieListResponse>> {
        return movieRepository.getMoviesList()
    }

}