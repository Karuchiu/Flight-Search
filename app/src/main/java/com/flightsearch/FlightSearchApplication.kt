package com.flightsearch

import android.app.Application
import com.flightsearch.data.AppDatabase

class FlightSearchApplication: Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this)}
}