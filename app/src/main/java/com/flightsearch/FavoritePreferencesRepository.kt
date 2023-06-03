package com.flightsearch

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

class FavoritePreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {

}
