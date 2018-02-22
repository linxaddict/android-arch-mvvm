package com.machineinsight_it.androidarch.mvvm.ui.main

import android.databinding.ObservableBoolean
import com.machineinsight_it.androidarch.mvvm.R
import com.machineinsight_it.androidarch.mvvm.api.service.ArchApi
import com.machineinsight_it.androidarch.mvvm.ui.base.events.TextMessageEvent
import com.machineinsight_it.androidarch.mvvm.ui.base.events.DataSetChangedEvent
import com.machineinsight_it.androidarch.mvvm.ui.base.model.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseViewModel() {
    @Inject
    lateinit var apiService: ArchApi

    val users = mutableListOf<UserViewModel>()
    var fetchInProgress = ObservableBoolean(false)

    val dataSetCleared = DataSetChangedEvent()
    val dataSetUpdated = DataSetChangedEvent()
    val showErrorMessage = TextMessageEvent()

    private fun finalizeDataSetUpdate() = fetchInProgress.set(false)

    private fun handleFetchError() = showErrorMessage.call(R.string.error_unable_to_fetch_users)

    private fun handleUsersFetched(it: MutableList<UserViewModel>) {
        users.addAll(it)
        dataSetUpdated.call(0, users.size)
    }

    private fun prepareForDataSetUpdate() {
        val size = users.size

        users.clear()
        dataSetCleared.call(0, size)
        fetchInProgress.set(true)
    }

    fun fetchUsers() {
        val disposable = apiService.fetchApiUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { prepareForDataSetUpdate() }
                .doFinally { finalizeDataSetUpdate() }
                .flatMapIterable { it }
                .map { UserViewModel(it) }
                .toList()
                .subscribe(
                        { handleUsersFetched(it) },
                        { handleFetchError() }
                )

        registerDisposable(disposable)
    }
}