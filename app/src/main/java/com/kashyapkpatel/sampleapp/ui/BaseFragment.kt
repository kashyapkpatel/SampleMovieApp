package com.kashyapkpatel.sampleapp.ui

import android.content.Context
import com.kashyapkpatel.sampleapp.di.ViewModelProviderFactory
import com.kashyapkpatel.sampleapp.interfaces.IFragmentCallbacks
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment : DaggerFragment() {

    lateinit var iFragmentCallbacks: IFragmentCallbacks

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

}