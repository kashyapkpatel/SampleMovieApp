package com.kashyapkpatel.sampleapp.di.module

import com.kashyapkpatel.sampleapp.ui.AppInfoFragment
import com.kashyapkpatel.sampleapp.ui.MovieListFragment
import com.kashyapkpatel.sampleapp.ui.LoginFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeMovieListFragment(): MovieListFragment

    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeAppInfoFragment(): AppInfoFragment
}