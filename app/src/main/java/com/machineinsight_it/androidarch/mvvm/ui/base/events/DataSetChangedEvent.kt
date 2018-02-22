package com.machineinsight_it.androidarch.mvvm.ui.base.events

import android.arch.lifecycle.MutableLiveData

class DataSetChangedEvent : MutableLiveData<Pair<Int, Int>>() {
    fun call(start: Int, end: Int) {
        value = Pair(start, end)
    }
}