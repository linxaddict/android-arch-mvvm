package com.machineinsight_it.androidarch.mvvm.ui.base.model

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.databinding.BaseObservable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {
    private val disposables = CompositeDisposable()

    fun registerDisposable(d: Disposable) {
        disposables.add(d)
    }

    fun clearDisposables() {
        disposables.clear()
    }
}