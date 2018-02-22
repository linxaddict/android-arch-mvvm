package com.machineinsight_it.androidarch.mvvm.ui.base.events

import android.arch.lifecycle.MutableLiveData

class TextMessageEvent : MutableLiveData<Int>() {
    fun call(stringId: Int) {
        value = stringId
    }
}