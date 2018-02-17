package com.machineinsight_it.androidarch.mvvm.ui.login

import android.databinding.ObservableField
import com.machineinsight_it.androidarch.mvvm.R
import com.machineinsight_it.androidarch.mvvm.ui.base.events.NavigationEvent
import com.machineinsight_it.androidarch.mvvm.ui.base.events.SnackBarEvent
import com.machineinsight_it.androidarch.mvvm.ui.base.model.BaseViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor() : BaseViewModel() {
    val email = ObservableField<String>()
    val emailError = ObservableField<Int>()
    val password = ObservableField<String>()
    val passwordError = ObservableField<Int>()

    val openMainScreen = NavigationEvent()
    val showErrorMessage = SnackBarEvent()

    fun login() {
//        openMainScreen.call()
        emailError.set(R.string.error_invalid_email)
    }
}