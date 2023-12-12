package com.flightsearch.di

import android.content.Context
import com.flightsearch.db.AppDatabase
import com.flightsearch.data.FlightRepository
import com.flightsearch.data.OfflineFlightRepository

/**
 * App Container for Dependency Injection
 */

interface AppContainer{
    val flightRepository: FlightRepository
}

/**
 * [AppContainer] implementation that provides an instance of [OfflineFlightRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer{
    //implementation for [FlightRepository]

    override val flightRepository: FlightRepository by lazy {
        OfflineFlightRepository(AppDatabase.getDatabase(context).flightDao())
    }
}