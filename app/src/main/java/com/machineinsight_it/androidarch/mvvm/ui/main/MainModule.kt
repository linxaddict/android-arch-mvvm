package com.machineinsight_it.androidarch.mvvm.ui.main

import android.arch.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides

@Module
class MainModule {
    @Provides
    fun provideMainViewModelFactory(factory: MainViewModelFactory): ViewModelProvider.Factory = factory
}