package com.flightsearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.flightsearch.FlightSearchApplication
import com.flightsearch.data.Airport
import com.flightsearch.data.AirportDao
import kotlinx.coroutines.flow.Flow

class FlightSearchViewModel(
    private val airPortDao: AirportDao
): ViewModel() {
    fun getFullSchedule(): Flow<List<Airport>> = airPortDao.getAll()

    companion object{
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FlightSearchApplication)
                FlightSearchViewModel(application.database.airportDao())
            }
        }
    }
}