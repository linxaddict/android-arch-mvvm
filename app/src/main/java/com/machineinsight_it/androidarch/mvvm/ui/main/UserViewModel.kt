package com.machineinsight_it.androidarch.mvvm.ui.main

import android.databinding.ObservableField
import com.machineinsight_it.androidarch.mvvm.api.model.ApiUser
import com.machineinsight_it.androidarch.mvvm.ui.base.model.RowViewModel

class UserViewModel(apiUser: ApiUser) : RowViewModel {
    val login = ObservableField<String>()
    val firstName = ObservableField<String>()
    val lastName = ObservableField<String>()
    val avatar = ObservableField<String>()

    init {
        login.set(apiUser.login)
        firstName.set(apiUser.firstName)
        lastName.set(apiUser.lastName)
        avatar.set(apiUser.avatar)
    }
}