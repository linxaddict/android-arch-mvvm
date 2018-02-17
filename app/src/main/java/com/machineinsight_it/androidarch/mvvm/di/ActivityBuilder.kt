package com.machineinsight_it.androidarch.mvvm.di

import com.machineinsight_it.androidarch.mvvm.ui.main.MainActivity
import com.machineinsight_it.androidarch.mvvm.ui.main.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun bindMainActivity(): MainActivity
}