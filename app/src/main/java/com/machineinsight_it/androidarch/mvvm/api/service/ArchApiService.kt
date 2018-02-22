package com.machineinsight_it.androidarch.mvvm.api.service

import com.machineinsight_it.androidarch.mvvm.api.model.ApiUser
import com.machineinsight_it.androidarch.mvvm.api.model.AuthResult
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class ArchApiService : ArchApi {
    private fun createUsers(): List<ApiUser> {
        val users = mutableListOf<ApiUser>()
        users.add(ApiUser("User1", "User1", "Test",
                "https://icdn.2cda.pl/obr/thumbs/86aa101d44209660c7ac999dd7fc6a09.jpg_oooooooooo_273x.jpg"))
        users.add(ApiUser("User2", "User2", "Test",
                "https://icdn.2cda.pl/obr/thumbs/86aa101d44209660c7ac999dd7fc6a09.jpg_oooooooooo_273x.jpg"))
        users.add(ApiUser("User3", "User3", "Test",
                "https://icdn.2cda.pl/obr/thumbs/86aa101d44209660c7ac999dd7fc6a09.jpg_oooooooooo_273x.jpg"))
        users.add(ApiUser("User4", "User4", "Test",
                "https://icdn.2cda.pl/obr/thumbs/86aa101d44209660c7ac999dd7fc6a09.jpg_oooooooooo_273x.jpg"))
        users.add(ApiUser("User5", "User5", "Test",
                "https://icdn.2cda.pl/obr/thumbs/86aa101d44209660c7ac999dd7fc6a09.jpg_oooooooooo_273x.jpg"))

        return users
    }

    override fun fetchApiUsers(): Observable<List<ApiUser>> {
        return Observable.just(createUsers())
    }

    override fun login(email: String, password: String): Observable<AuthResult> {
        return Observable
                .just(AuthResult("email@test.com", "Test", "User"))
                .delay(2L, TimeUnit.SECONDS)
    }

}