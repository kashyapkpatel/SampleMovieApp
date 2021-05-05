package com.kashyapkpatel.sampleapp.di.module

import com.kashyapkpatel.sampleapp.ui.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [FragmentModule::class, ViewModelModule::class])
    abstract fun contributeHomeActivity(): HomeActivity
}