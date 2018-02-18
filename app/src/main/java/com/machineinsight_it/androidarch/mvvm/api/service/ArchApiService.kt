package com.machineinsight_it.androidarch.mvvm.api.service

import com.machineinsight_it.androidarch.mvvm.api.model.ApiUser
import com.machineinsight_it.androidarch.mvvm.api.model.AuthResult
import io.reactivex.Observable

class ArchApiService : ArchApi {
    override fun fetchApiUsers(): Observable<ApiUser> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun login(email: String, password: String): Observable<AuthResult> {
        return Observable.just(AuthResult("email@test.com", "Test", "User"))
    }

}