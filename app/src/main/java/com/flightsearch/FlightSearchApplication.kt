package com.flightsearch

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.flightsearch.data.UserPreferencesRepository
import com.flightsearch.di.AppContainer
import com.flightsearch.di.AppDataContainer

private const val FAVORITE_PREFERENCES_NAME = "favorite_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = FAVORITE_PREFERENCES_NAME
)
class FlightSearchApplication: Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    lateinit var favoritePreferencesRepository: UserPreferencesRepository

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
        favoritePreferencesRepository = UserPreferencesRepository(dataStore)
    }

}