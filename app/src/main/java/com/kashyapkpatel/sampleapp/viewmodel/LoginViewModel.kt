package com.kashyapkpatel.sampleapp.viewmodel

import androidx.lifecycle.ViewModel
import com.kashyapkpatel.sampleapp.util.ActionLiveData
import javax.inject.Inject

/**
 * Common ViewModel class for Login Module.
 */
class LoginViewModel @Inject constructor() : ViewModel() {

    var actionLiveDataLoginClicked = ActionLiveData<Unit>()

    fun login() {
        actionLiveDataLoginClicked.sendAction(Unit)
    }

}