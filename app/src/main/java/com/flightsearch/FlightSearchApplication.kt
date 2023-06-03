package com.flightsearch

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.flightsearch.data.AppDatabase

private const val FAVORITE_PREFERENCES_NAME = "favorite_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = FAVORITE_PREFERENCES_NAME
)
class FlightSearchApplication: Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this)}

    lateinit var favoritePrefencesRepository: FavoritePreferencesRepository

    override fun onCreate() {
        super.onCreate()
        favoritePrefencesRepository = FavoritePreferencesRepository(dataStore)
    }

}