package com.flightsearch

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey

class FavoritePreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val IS_FAVORITE = booleanPreferencesKey("is_favorite")
    }
}
