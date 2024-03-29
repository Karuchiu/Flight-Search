package com.flightsearch.flight.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flightsearch.data.FlightRepository
import com.flightsearch.flight.FlightScreenDestination
import com.flightsearch.models.Airport
import com.flightsearch.models.Favorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FlightUiState(
    val code: String = "",
    val favoriteList: List<Favorite> = emptyList(),
    val destinationList: List<Airport> = emptyList(),
    val departureAirport: Airport = Airport()
)

@HiltViewModel
class FlightViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val flightRepository: FlightRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(FlightUiState())
    val uiState = _uiState.asStateFlow()

    private val airportCode: String =
        checkNotNull(savedStateHandle[FlightScreenDestination.codeArg])

    private val _favoriteStatusMap =
        MutableStateFlow<Map<Pair<String, String>, Boolean>>(emptyMap())
    val favoriteStatusMapFlow: StateFlow<Map<Pair<String, String>, Boolean>> = _favoriteStatusMap

    init {
        viewModelScope.launch {
            processFlightList(airportCode)
        }
    }

    private fun processFlightList(airportCode: String) {
        _uiState.update { it.copy(code = airportCode) }

        viewModelScope.launch {
            val favoritesFlow = flightRepository.getAllFavorites()
            val airportsFlow = flightRepository.getAllAirportsByCode(airportCode)
            val departureAirport = flightRepository.getAirportByCodeFlow(airportCode)

            combine(
                favoritesFlow,
                airportsFlow,
                departureAirport
            ) { favorites, airports, departurePort ->
                Log.d("FlightVM", "Favs collected: $favorites")

                // Now you can update your UI state with both favorites and airports data
                _uiState.value = uiState.value.copy(
                    favoriteList = favorites,
                    destinationList = airports,
                    departureAirport = departurePort
                )
            }.collect()
        }
    }

    private fun updateFavoriteStatusMap(updatedMap: Map<Pair<String, String>, Boolean>) {
        _favoriteStatusMap.value = updatedMap
    }

    fun addFavoriteFlight(departureCode: String, destinationCode: String) {
        viewModelScope.launch {
            val favoriteFlow: Flow<Favorite?> =
                flightRepository.getSingleFavorite(departureCode, destinationCode)

            favoriteFlow.collect { favorite ->
                if (favorite != null) {
                    flightRepository.deleteFavoriteFlight(favorite)
                    updateFavoriteStatusMap(
                        _favoriteStatusMap.value + (Pair(departureCode, destinationCode) to false)
                    )
                } else {
                    val newFavorite = Favorite(
                        departureCode = departureCode,
                        destinationCode = destinationCode
                    )
                    flightRepository.insertFavoriteFlight(newFavorite)
                    updateFavoriteStatusMap(
                        _favoriteStatusMap.value + (Pair(departureCode, destinationCode) to true)
                    )
                }

                flightRepository.getAllFavorites().collect {
                    _uiState.value = uiState.value.copy(favoriteList = it)
                }
            }
        }
    }
}