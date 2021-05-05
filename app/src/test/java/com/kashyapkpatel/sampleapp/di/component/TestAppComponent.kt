package com.kashyapkpatel.sampleapp.di.component

import com.kashyapkpatel.sampleapp.TestApplication
import com.kashyapkpatel.sampleapp.di.module.*
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        TestRetrofitModule::class,
        TestRepoModule::class,
        AndroidSupportInjectionModule::class,
        ActivityModule::class,
        FragmentModule::class,
        ViewModelFactoryModule::class,
        ViewModelModule::class
    ]
)
interface TestAppComponent : AndroidInjector<TestApplication> {
    @Component.Builder
    abstract class Builder: AndroidInjector.Builder<TestApplication>()
}