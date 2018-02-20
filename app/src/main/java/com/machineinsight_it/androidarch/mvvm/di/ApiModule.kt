package com.machineinsight_it.androidarch.mvvm.di

import com.machineinsight_it.androidarch.mvvm.api.service.ArchApi
import com.machineinsight_it.androidarch.mvvm.api.service.ArchApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiModule {
    @Provides
    @Singleton
    fun provideArchApi(): ArchApi {
        return ArchApiService()
    }
}