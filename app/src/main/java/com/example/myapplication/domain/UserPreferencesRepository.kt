package com.example.myapplication.domain

import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.myapplication.models.User

interface IUserPreferencesRepository {
    suspend fun getUser(): User?
    suspend fun saveUser(user: User?)
}

private object PreferencesKeys {
    val PREF_USER = stringPreferencesKey("pref_user")
}

class UserPreferencesRepository: IUserPreferencesRepository{

    override suspend fun getUser(): User? {
        return User(
            id = 0,
            email = "tim.nguyen@solunar.de",
            firstName = "Tim",
            lastName = "Nguyen",
            accessToken = "access_token",
            refreshToken = "refresh_token",
            userRole = "user"
        )
    }

    override suspend fun saveUser(user: User?) {

    }
}
