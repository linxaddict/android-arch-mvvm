package com.machineinsight_it.androidarch.mvvm.di

import com.machineinsight_it.androidarch.mvvm.ui.login.LoginActivity
import com.machineinsight_it.androidarch.mvvm.ui.login.LoginModule
import com.machineinsight_it.androidarch.mvvm.ui.main.MainActivity
import com.machineinsight_it.androidarch.mvvm.ui.main.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun bindLoginActivity(): LoginActivity
}