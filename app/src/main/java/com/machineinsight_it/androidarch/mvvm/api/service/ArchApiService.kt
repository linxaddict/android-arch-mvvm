package com.machineinsight_it.androidarch.mvvm.api.service

import com.machineinsight_it.androidarch.mvvm.api.model.ApiUser
import com.machineinsight_it.androidarch.mvvm.api.model.AuthResult
import io.reactivex.Observable

class ArchApiService : ArchApi {
    private fun createUsers(): List<ApiUser> {
        val users = mutableListOf<ApiUser>()
        users.add(ApiUser("User1", "User1", "Test", null))
        users.add(ApiUser("User2", "User2", "Test", null))
        users.add(ApiUser("User3", "User3", "Test", null))
        users.add(ApiUser("User4", "User4", "Test", null))
        users.add(ApiUser("User5", "User5", "Test", null))

        return users
    }

    override fun fetchApiUsers(): Observable<List<ApiUser>> {
        return Observable.just(createUsers())
    }

    override fun login(email: String, password: String): Observable<AuthResult> {
        return Observable.just(AuthResult("email@test.com", "Test", "User"))
    }

}