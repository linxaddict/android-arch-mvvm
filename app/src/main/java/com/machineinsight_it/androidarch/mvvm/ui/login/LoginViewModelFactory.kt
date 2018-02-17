package com.machineinsight_it.androidarch.mvvm.ui.login

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

class LoginViewModelFactory @Inject constructor(private val viewModel: LoginViewModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                viewModel as T
            } else {
                throw IllegalArgumentException("Unknown class name")
            }
}