package com.flightsearch.ui.screens.flight_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.flightsearch.models.Airport
import com.flightsearch.models.Favorite
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FlightViewModel(
    savedStateHandle: SavedStateHandle,
    val flightRepository: FlightRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(FlightUiState())
    val uiState: StateFlow<FlightUiState> = _uiState

    private val airportCode: String = savedStateHandle[FlightScreenDestination.codeArg] ?: ""

    var flightAdded: Boolean by mutableStateOf(false)

    private var getFavoriteJob: Job? = null

    private var favoriteFlights = mutableStateListOf<Favorite>()
    private var allAirports = mutableStateListOf<Airport>()
    private var destinationAirports = mutableStateListOf<Airport>()

    init {
        viewModelScope.launch {
            processFlightList(airportCode)
        }
    }

    private fun processFlightList(airportCode: String) {

        viewModelScope.launch {
           /* flightRepository.getAllFavorites().collect{
                favoriteFlights.clear()
                favoriteFlights.addAll(it)
            }*/

            getFavoriteJob?.cancel()

            getFavoriteJob = flightRepository.getAllFavorites()
                .onEach { list ->
                    _uiState.update {
                        _uiState.value.copy(
                            favoriteList = list
                        )
                    }
                }.launchIn(viewModelScope)

            flightRepository.getAllAirports().collect{
                allAirports.clear()
                allAirports.addAll(it)
            }

            val aa = flightRepository.getAllAirports()
            flightRepository.getAllAirportsByCode(airportCode).collect{
                destinationAirports.clear()
                destinationAirports.addAll(it)
            }

            val departureAirport = allAirports.first {it.iataCode == airportCode}
            _uiState.update{
                it.copy(
                    code = airportCode,
                    favoriteList = it.favoriteList,
                    destinationList = destinationAirports,
                    departureAirport = departureAirport
                )
            }
        }
    }

    fun addFavoriteFlight(departureCode: String, destinationCode: String){
        viewModelScope.launch {
            val favorite: Favorite = flightRepository.getSingleFavorite(departureCode, destinationCode)

            if (favorite == null){
                val tmp = Favorite(
                    departureCode = departureCode,
                    destinationCode = destinationCode
                )
                flightAdded = true
                flightRepository.insertFavoriteFlight(tmp)
            }else{
                flightAdded = false
                flightRepository.deleteFavoriteFlight(favorite)
            }

            // Cheating, I am forcing a Recomposition
            // I should be using Flow but am not sure how to atm
            flightRepository.getAllFavorites().collect{
                favoriteFlights.clear()
                favoriteFlights.addAll(it)
            }
            _uiState.update {
                uiState.value.copy(
                    favoriteList = favoriteFlights,
                )
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