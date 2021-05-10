package com.kashyapkpatel.sampleapp.di.module

import androidx.lifecycle.ViewModel
import com.kashyapkpatel.sampleapp.di.ViewModelKey
import com.kashyapkpatel.sampleapp.viewmodel.HomeViewModel
import com.kashyapkpatel.sampleapp.viewmodel.LoginViewModel
import com.kashyapkpatel.sampleapp.viewmodel.MovieViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    abstract fun bindMovieViewModel(movieViewModel: MovieViewModel): ViewModel
}