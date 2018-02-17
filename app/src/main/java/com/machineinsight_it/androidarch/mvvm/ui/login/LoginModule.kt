package com.machineinsight_it.androidarch.mvvm.ui.login

import android.arch.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides

@Module
class LoginModule {
    @Provides
    fun provideLoginViewModelFactory(factory: LoginViewModelFactory): ViewModelProvider.Factory = factory
}