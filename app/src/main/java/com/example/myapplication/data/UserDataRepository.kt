package com.example.myapplication.data

import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.myapplication.models.User

interface IUserDataRepository {
    suspend fun getUser(): User?
    suspend fun saveUser(user: User?)
}

private object PreferencesKeys {
    val PREF_USER = stringPreferencesKey("pref_user")
}

class UserDataRepository: IUserDataRepository{

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
