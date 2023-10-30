package com.flightsearch.ui.screens.flight_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.flightsearch.FlightSearchApplication
import com.flightsearch.data.FlightRepository
import com.flightsearch.models.Favorite
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FlightViewModel(
    savedStateHandle: SavedStateHandle,
    val flightRepository: FlightRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(FlightUiState())
    val uiState = _uiState.asStateFlow()

    private val airportCode: String = savedStateHandle[FlightScreenDestination.codeArg] ?: ""

    var flightAdded: Boolean by mutableStateOf(false)

    //private var getFavoriteJob: Job? = null

    init {
        Log.d("FlightVM", "Before collecting airports")
        viewModelScope.launch {
            processFlightList(airportCode)
        }
        Log.d("FlightVM", "After collecting airports")
    }

    private fun processFlightList(airportCode: String) {
        _uiState.update { it.copy(code = airportCode) }

        viewModelScope.launch {
            val favoritesFlow = flightRepository.getAllFavorites()
            val airportsFlow = flightRepository.getAllAirports()

            combine(favoritesFlow, airportsFlow) { favorites, airports ->
                Log.d("FlightVM", "Favs collected: $favorites")
                Log.d("FlightVM", "Airports collected: ${airports.size}")

                // Now you can update your UI state with both favorites and airports data
                _uiState.value = uiState.value.copy(
                    favoriteList = favorites,
                    destinationList = airports
                )

                val departureAirport = airports.first { it.iataCode == airportCode }
                _uiState.update {
                    it.copy(
                        departureAirport = departureAirport
                    )
                }
            }.collect()
        }
    }

    fun addFavoriteFlight(departureCode: String, destinationCode: String){
        viewModelScope.launch {
            val favorite: Favorite = flightRepository.getSingleFavorite(departureCode, destinationCode)

            flightAdded = false
            flightRepository.deleteFavoriteFlight(favorite)

            // Cheating, I am forcing a Recomposition
            // I should be using Flow but am not sure how to atm
            flightRepository.getAllFavorites().collect{
                _uiState.value = _uiState.value.copy(favoriteList = it)
            }

        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FlightSearchApplication)
                val flightRepository = application.container.flightRepository
                FlightViewModel(
                    this.createSavedStateHandle(),
                    flightRepository = flightRepository
                )
            }
        }
    }
}