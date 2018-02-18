package com.machineinsight_it.androidarch.mvvm.api.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class ApiUser(val login: String, val firstName: String?, val lastName: String?,
                   val avatar: String?) : Parcelable