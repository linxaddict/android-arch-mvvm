package com.machineinsight_it.androidarch.mvvm.ui.base.events

import android.arch.lifecycle.MutableLiveData

class NavigationEvent : MutableLiveData<Void>() {
    fun call() {
        value = null
    }
}