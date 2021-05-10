package com.kashyapkpatel.sampleapp.viewmodel

import androidx.lifecycle.ViewModel
import com.kashyapkpatel.sampleapp.util.ActionLiveData
import javax.inject.Inject

/**
 * Common ViewModel class for Home Screen.
 */
class HomeViewModel @Inject constructor() : ViewModel() {

    var actionLiveDataShowAppInfoClicked = ActionLiveData<Unit>()
    var actionLiveDataOnThemeChanged = ActionLiveData<Boolean>()

    fun showAppInfo() {
        actionLiveDataShowAppInfoClicked.sendAction(Unit)
    }

    fun onThemeChanged(checked: Boolean) {
        actionLiveDataOnThemeChanged.sendAction(checked)
    }

}