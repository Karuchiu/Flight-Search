package com.flightsearch.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.flightsearch.data.FlightRepository
import com.flightsearch.data.OfflineFlightRepository
import com.flightsearch.data.UserPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

private const val FAVORITE_PREFERENCES_NAME = "favorite_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = FAVORITE_PREFERENCES_NAME
)

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Singleton
    @Provides
    fun provideUserPreferencesRepository(dataStore: DataStore<Preferences>): UserPreferencesRepository{
        return UserPreferencesRepository(dataStore)
    }

    @Provides
    @Singleton
    fun provideDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface AppModuleInt {

        @Binds
        @Singleton
        fun provideFlightRepository(repo: OfflineFlightRepository): FlightRepository
    }
}