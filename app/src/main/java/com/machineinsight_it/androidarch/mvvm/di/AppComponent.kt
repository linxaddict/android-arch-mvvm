package com.machineinsight_it.androidarch.mvvm.di

import android.app.Application
import com.machineinsight_it.androidarch.mvvm.AndroidArchApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
        AndroidInjectionModule::class, AppModule::class, ValidationModule::class,
        ActivityBuilder::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(application: AndroidArchApplication)
}