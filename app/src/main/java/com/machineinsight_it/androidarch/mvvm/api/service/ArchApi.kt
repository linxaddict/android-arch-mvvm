package com.machineinsight_it.androidarch.mvvm.api.service

import com.machineinsight_it.androidarch.mvvm.api.model.ApiUser
import com.machineinsight_it.androidarch.mvvm.api.model.AuthResult
import io.reactivex.Observable

interface ArchApi {
    fun login(email: String, password: String): Observable<AuthResult>

    fun fetchApiUsers(): Observable<List<ApiUser>>
}