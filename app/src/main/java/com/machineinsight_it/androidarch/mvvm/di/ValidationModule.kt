package com.machineinsight_it.androidarch.mvvm.di

import com.machineinsight_it.androidarch.mvvm.validation.EmailValidator
import com.machineinsight_it.androidarch.mvvm.validation.PasswordLengthValidator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ValidationModule {
    @Provides
    @Singleton
    fun provideEmailValidator(): EmailValidator {
        return EmailValidator()
    }

    @Provides
    @Singleton
    fun providePasswordLengthValidator(): PasswordLengthValidator {
        return PasswordLengthValidator()
    }
}