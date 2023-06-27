package com.flightsearch.ui.screens.search

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.room.Query
import com.flightsearch.FlightSearchApplication
import com.flightsearch.data.UserPreferencesRepository
import com.flightsearch.data.FlightRepository
import com.flightsearch.models.Airport
import com.flightsearch.models.Favorite
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    val flightRepository: FlightRepository,
    private val favoritePreferencesRepository: UserPreferencesRepository
) : ViewModel(){

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    private var getAirportsJob: Job? = null

    private var airportList = mutableListOf<Airport>()
    private var favoriteList = mutableListOf<Favorite>()

    init {
        viewModelScope.launch {
            favoritePreferencesRepository.userPreferencesFlow.collect{
                processSearchQueryFlow(it.searchValue)
            }
        }
    }

    private fun processSearchQueryFlow(query: String){
        _uiState.update { it.copy(searchQuery = query) }

        if (query.isEmpty()){
            viewModelScope.launch {
                flightRepository.getAllAirports().collect { airports ->
                    airportList.clear()
                    airportList.addAll(airports)
                }
                flightRepository.getAllFavorites().collect(){
                    favoriteList.clear()
                    favoriteList.addAll(it)
                }
                _uiState.update {
                    _uiState.value.copy(
                        airportList = airportList,
                        favoriteList = favoriteList
                    )
                }
            }
        } else {
            getAirportsJob?.cancel()

            getAirportsJob = flightRepository.getAirportsByInput(query)
            //on each emission
                .onEach {result ->
                    _uiState.update {
                        _uiState.value.copy(
                            airportList = result
                        )
                    }
                }.launchIn(viewModelScope)
        }
    }

    fun updateQuery(searchQuery: String){
        _uiState.update {
            it.copy(
                searchQuery = searchQuery
            )
        }
        updatePreferenceSearchValue(searchQuery)
    }

    /**
     *  Essence of this code?
     *  [Why does this not have viewModelScope while others have]
     **/
    fun updateSelectedCode(selectedCode: String){
        _uiState.update {
            it.copy(
                selectedCode = selectedCode
            )
        }
    }

    fun onQueryChange(query: String){
        processSearchQueryFlow(query)
    }

    private fun updatePreferenceSearchValue(newValue: String) {
        viewModelScope.launch {
            favoritePreferencesRepository.updateUserPreferences(
                searchValue = newValue
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FlightSearchApplication)
                val flightRepository = application.container.flightRepository
                val preferencesRepository = application.favoritePreferencesRepository
                SearchViewModel(
                    flightRepository = flightRepository,
                    favoritePreferencesRepository = preferencesRepository
                )
            }
        }
    }
}