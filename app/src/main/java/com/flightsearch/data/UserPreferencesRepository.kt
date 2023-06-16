package com.flightsearch.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

data class UserPreferences(
    val searchValue: String = ""
)

// Define a DataStore class to store and retrieve your data
class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val SEARCH_VALUE = stringPreferencesKey("search_value")
        const val TAG = "UserPreferencesRepo"
    }

    //Update the user preferences
    suspend fun updateUserPreferences(searchValue: String){
        dataStore.edit {mutablePreferences ->  
            mutablePreferences[SEARCH_VALUE] = searchValue
        }
    }

    // Read the user preferences as a Flow
    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch {
            if (it is IOException){
                Log.e(TAG, "Error reading preferences. ", it )
                emit(emptyPreferences())
            } else{
                throw it
            }
        }
        .map { preferences ->
        UserPreferences(
            searchValue = preferences[SEARCH_VALUE] ?: ""
        )
    }
}
