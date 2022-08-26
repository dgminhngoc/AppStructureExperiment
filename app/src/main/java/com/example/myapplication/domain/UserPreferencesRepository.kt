package com.example.myapplication.domain

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.myapplication.models.User

interface IUserPreferencesRepository {
    suspend fun getUser(): User
    suspend fun saveUser(user: User)
}

private object PreferencesKeys {
    val PREF_USER = stringPreferencesKey("pref_user")
}

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>,
): IUserPreferencesRepository{

    override suspend fun getUser(): User {
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

    override suspend fun saveUser(user: User) {

    }

//    val userPreferencesFlow: Flow<Unit> = dataStore.data
//        .catch { exception ->
//            // dataStore.data throws an IOException when an error is encountered when reading data
//            if (exception is IOException) {
//
//                emit(UserPreferences.PrefsEmpty)
//            } else {
//                throw exception
//            }
//        }.map { prefs ->
//            mapUserPreferences(prefs)
//        }
//
//    private fun mapUserPreferences(prefs: UserPreferences): UserPreferences {
//        // Get the sort order from preferences and convert it to a [SortOrder] object
//        val sortOrder =
//            SortOrder.valueOf(
//                preferences[PreferencesKeys.SORT_ORDER] ?: SortOrder.NONE.name
//            )
//
//        // Get our show completed value, defaulting to false if not set:
//        val showCompleted = preferences[PreferencesKeys.SHOW_COMPLETED] ?: false
//        return UserPreferences(showCompleted, sortOrder)
//    }
}
