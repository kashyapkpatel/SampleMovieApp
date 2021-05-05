package com.kashyapkpatel.sampleapp.di.module

import android.content.Context
import com.kashyapkpatel.sampleapp.util.TestDispatcherProvider
import com.kashyapkpatel.sampleapp.network.api.MoviesApi
import com.kashyapkpatel.sampleapp.network.repo.DefaultMovieRepository
import com.kashyapkpatel.sampleapp.network.repo.MovieRepository
import com.kashyapkpatel.sampleapp.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.test.TestCoroutineDispatcher

@Module
class TestRepoModule {

    /**
     * The method returns the TestCoroutineDispatcher object
     */
    @Provides
    fun provideTestCoroutineDispatcher(): TestCoroutineDispatcher {
        return TestCoroutineDispatcher()
    }

    /**
     * The method returns the DispatcherProvider object
     */
    @Provides
    fun provideTestDispatcherProvider(testCoroutineDispatcher: TestCoroutineDispatcher): DispatcherProvider {
        return TestDispatcherProvider(testCoroutineDispatcher)
    }

    /**
     * The method returns the DefaultMovieRepository object
     */
    @Provides
    fun provideDefaultMovieRepository(context: Context,
                                      dispatcherProvider: TestDispatcherProvider,
                                      moviesApi: MoviesApi
    ): MovieRepository {
        return DefaultMovieRepository(context, dispatcherProvider, moviesApi)
    }
}