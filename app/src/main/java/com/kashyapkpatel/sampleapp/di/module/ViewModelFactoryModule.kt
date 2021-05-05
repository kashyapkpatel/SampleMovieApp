package com.kashyapkpatel.sampleapp.di.module

import androidx.lifecycle.ViewModelProvider
import com.kashyapkpatel.sampleapp.di.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelProvideFactory: ViewModelProviderFactory): ViewModelProvider.Factory
}