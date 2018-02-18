package com.machineinsight_it.androidarch.mvvm.api.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class AuthResult(val email: String, val firstName: String?, val lastName: String?) : Parcelable