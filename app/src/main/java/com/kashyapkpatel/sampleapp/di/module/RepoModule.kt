package com.kashyapkpatel.sampleapp.di.module

import android.content.Context
import com.kashyapkpatel.sampleapp.network.api.MoviesApi
import com.kashyapkpatel.sampleapp.network.repo.DefaultMovieRepository
import com.kashyapkpatel.sampleapp.network.repo.MovieRepository
import com.kashyapkpatel.sampleapp.util.DefaultDispatcherProvider
import com.kashyapkpatel.sampleapp.util.DispatcherProvider
import dagger.Module
import dagger.Provides

@Module
class RepoModule {

    /**
     * The method returns the DispatcherProvider object
     */
    @Provides
    fun provideDefaultDispatcherProvider(): DispatcherProvider {
        return DefaultDispatcherProvider()
    }

    /**
     * The method returns the DefaultMovieRepository object
     */
    @Provides
    fun provideDefaultMovieRepository(
        context: Context,
        dispatcherProvider: DefaultDispatcherProvider,
        moviesApi: MoviesApi
    ): MovieRepository {
        return DefaultMovieRepository(context, dispatcherProvider, moviesApi)
    }
}