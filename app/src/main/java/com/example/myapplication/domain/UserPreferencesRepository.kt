package com.example.myapplication.domain

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.myapplication.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

sealed class UserPreferences {
    data class PrefsUser(val user: User?) : UserPreferences()
    object PrefsEmpty: UserPreferences()
}

interface  IUserPreferencesRepository {
}

private object PreferencesKeys {
    val PREF_USER = stringPreferencesKey("pref_user")
}

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>,
): IUserPreferencesRepository{

    fun initUserData() {

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
