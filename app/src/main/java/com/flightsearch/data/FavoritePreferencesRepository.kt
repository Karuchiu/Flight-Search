package com.flightsearch.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class FavoritePreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val IS_FAVORITE = booleanPreferencesKey("is_favorite")
        const val TAG = "FavPreferencesRepo"
    }

    suspend fun saveFavoritePreferences(isFavorite: Boolean){
        dataStore.edit {mutablePreferences ->  
            mutablePreferences[IS_FAVORITE] = isFavorite
        }
    }

    val isFavorite: Flow<Boolean> = dataStore.data
        .catch {
            if (it is IOException){
                Log.e(TAG, "Error reading preferences. ", it )
                emit(emptyPreferences())
            } else{
                throw it
            }
        }
        .map { preferences ->
        preferences[IS_FAVORITE] ?: true
    }
}
